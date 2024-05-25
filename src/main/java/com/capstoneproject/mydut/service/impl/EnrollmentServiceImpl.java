package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.common.enums.EnrollmentStatus;
import com.capstoneproject.mydut.common.enums.ErrorCode;
import com.capstoneproject.mydut.domain.entity.EnrollmentEntity;
import com.capstoneproject.mydut.domain.repository.ClassRepository;
import com.capstoneproject.mydut.domain.repository.EnrollmentRepository;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import com.capstoneproject.mydut.exception.ObjectNotFoundException;
import com.capstoneproject.mydut.exception.ValidateException;
import com.capstoneproject.mydut.payload.request.enrollment.ApproveEnrollmentRequest;
import com.capstoneproject.mydut.payload.request.enrollment.NewEnrollmentRequest;
import com.capstoneproject.mydut.payload.response.ErrorDTO;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.EnrollmentService;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author vndat00
 * @since 5/13/2024
 */

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final SecurityUtils securityUtils;
    private final EnrollmentRepository enrollmentRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public Response<OnlyIdDTO> createEnrollment(NewEnrollmentRequest request) {
        var principal = securityUtils.getPrincipal();
        String classCode = request.getClassCode();

        var clazz = classRepository.findByClassCode(classCode).orElseThrow(() ->
                new ObjectNotFoundException("classCode", classCode));

        var currentUser = userRepository.findById(UUID.fromString(principal.getUserId())).orElseThrow(() ->
                new ObjectNotFoundException("userId", principal.getUserId()));

        EnrollmentEntity enrollment = new EnrollmentEntity();

        enrollment.setUser(currentUser);
        enrollment.setClazz(clazz);
        enrollment.setStatus(EnrollmentStatus.WAITING.getId());

        enrollment.prePersist(UUID.fromString(principal.getUserId()));

        var savedEnrollment = enrollmentRepository.save(enrollment);

        return Response.<OnlyIdDTO>newBuilder()
                .setSuccess(true)
                .setData(OnlyIdDTO.newBuilder()
                        .setId(savedEnrollment.getEnrollmentId().toString())
                        .build())
                .build();
    }

    @Override
    @Transactional
    public Response<NoContentDTO> approveEnrollment(ApproveEnrollmentRequest request) {
        var principal = securityUtils.getPrincipal();

        UUID classId = UUID.fromString(request.getClassId());
        List<UUID> userIds = request.getUserIds().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        var currentClass = classRepository.findById(classId).orElseThrow(() ->
                new ObjectNotFoundException("classId", classId));

        if (!principal.getUserId().equalsIgnoreCase(currentClass.getCreatedBy().toString())) {
            throw new ValidateException(List.of(ErrorDTO.of(principal.getUserId(), ErrorCode.ACCESS_DENIED)));
        }

        List<EnrollmentEntity> neededApproveEnrollments = enrollmentRepository.findAllByClassIdAndUserIds(classId, userIds);

        for (var enrollment : neededApproveEnrollments) {
            enrollment.setStatus(EnrollmentStatus.APPROVED.getId());
            enrollment.preUpdate(UUID.fromString(principal.getUserId()));
        }

        enrollmentRepository.saveAll(neededApproveEnrollments);

        List<EnrollmentEntity> approvedEnrollments = enrollmentRepository.findAllWithStatusApproved(classId);

        currentClass.setTotalStudent(approvedEnrollments.size());

        classRepository.save(currentClass);

        return Response.<NoContentDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Approved done.")
                .build();
    }
}
