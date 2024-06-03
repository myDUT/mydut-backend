package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.common.enums.EnrollmentStatus;
import com.capstoneproject.mydut.common.enums.ErrorCode;
import com.capstoneproject.mydut.converter.EnrollmentConverter;
import com.capstoneproject.mydut.domain.entity.EnrollmentEntity;
import com.capstoneproject.mydut.domain.projection.EnrolledStudent;
import com.capstoneproject.mydut.domain.repository.ClassRepository;
import com.capstoneproject.mydut.domain.repository.EnrollmentRepository;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import com.capstoneproject.mydut.exception.ObjectNotFoundException;
import com.capstoneproject.mydut.exception.ValidateException;
import com.capstoneproject.mydut.payload.request.enrollment.ActionEnrollmentRequest;
import com.capstoneproject.mydut.payload.request.enrollment.NewEnrollmentRequest;
import com.capstoneproject.mydut.payload.response.*;
import com.capstoneproject.mydut.service.EnrollmentService;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

        var clazz = classRepository.findByClassCode(classCode);

        if (clazz.isEmpty()) {
            return Response.<OnlyIdDTO>newBuilder()
                    .setSuccess(false)
                    .setMessage(String.format("No exist class with code %s", classCode))
                    .build();
        }

        var currentUser = userRepository.findById(UUID.fromString(principal.getUserId())).orElseThrow(() ->
                new ObjectNotFoundException("userId", principal.getUserId()));

        List<EnrollmentEntity> existEnrollments = enrollmentRepository.findAllByClassIdAndUserIds(clazz.get().getClassId(), List.of(currentUser.getUserId()));

        // existWaitingEnrollment always have only 1 element
        if (!existEnrollments.isEmpty()) {
            var existEnrollment = existEnrollments.get(0);

            if (EnrollmentStatus.WAITING.getId().equals(existEnrollment.getStatus())) {
                return Response.<OnlyIdDTO>newBuilder()
                        .setSuccess(false)
                        .setMessage("Enrollment is created. Please waiting approval from lecturer.")
                        .setErrorCode(ErrorCode.EXIST_WAITING_ENROLLMENT)
                        .build();
            } else if (EnrollmentStatus.APPROVED.getId().equals(existEnrollment.getStatus())) {
                return Response.<OnlyIdDTO>newBuilder()
                        .setSuccess(false)
                        .setMessage("You're a member of this class. No need to enroll anymore.")
                        .setErrorCode(ErrorCode.APPROVED_ENROLLMENT)
                        .build();
            }
        }


        EnrollmentEntity enrollment = new EnrollmentEntity();

        enrollment.setUser(currentUser);
        enrollment.setClazz(clazz.get());
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
    public Response<NoContentDTO> actionEnrollment(ActionEnrollmentRequest request) {
        var principal = securityUtils.getPrincipal();

        Integer actionType = request.getActionType();

        UUID classId = UUID.fromString(request.getClassId());
        List<UUID> userIds = request.getUserIds().stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());

        var currentClass = classRepository.findById(classId).orElseThrow(() ->
                new ObjectNotFoundException("classId", classId));

        if (!principal.getUserId().equalsIgnoreCase(currentClass.getCreatedBy().toString())) {
            throw new ValidateException(List.of(ErrorDTO.of(principal.getUserId(), ErrorCode.ACCESS_DENIED)));
        }

        List<EnrollmentEntity> neededActionEnrollments = enrollmentRepository.findAllByClassIdAndUserIds(classId, userIds);

        for (var enrollment : neededActionEnrollments) {
            if (!Objects.equals(EnrollmentStatus.REJECTED.getId(), enrollment.getStatus())){
                if (actionType == 1) {
                    enrollment.setStatus(EnrollmentStatus.APPROVED.getId());
                } else if (actionType == 0) {
                    enrollment.setStatus(EnrollmentStatus.REJECTED.getId());
                }
                enrollment.preUpdate(UUID.fromString(principal.getUserId()));
            }
        }

        enrollmentRepository.saveAll(neededActionEnrollments);

        List<EnrollmentEntity> changedEnrollments = enrollmentRepository.findAllWithStatusApproved(classId);

        currentClass.setTotalStudent(changedEnrollments.size());

        classRepository.save(currentClass);

        return Response.<NoContentDTO>newBuilder()
                .setSuccess(true)
                .setMessage(actionType == 1 ? "Approved successfully." : "Rejected successfully.")
                .build();
    }

    @Override
    public Response<List<EnrolledStudentDTO>> getAllEnrolledStudentByClassId(String request) {
        // TODO: validate request

        UUID classId = UUID.fromString(request);

        List<EnrolledStudent> enrolledStudents = enrollmentRepository.findAllEnrolledStudentByClassId(classId);

        List<EnrolledStudentDTO> enrolledStudentDTOs = enrolledStudents.stream().map(eS -> EnrollmentConverter.projection2Dto(eS).build())
                .collect(Collectors.toList());

        return Response.<List<EnrolledStudentDTO>>newBuilder()
                .setSuccess(true)
                .setMessage("Fetch list enrolled student by classId successfully.")
                .setData(enrolledStudentDTOs)
                .build();
    }

    @Override
    public Response<List<EnrolledStudentDTO>> getAllApprovedStudentByClassId(String request) {
        List<EnrolledStudent> approvedStudent = enrollmentRepository.findAllApprovedStudentByClassId(UUID.fromString(request));

        List<EnrolledStudentDTO> enrolledStudentDTOs = approvedStudent.stream().map(aS -> EnrollmentConverter.projection2Dto(aS).build())
                .collect(Collectors.toList());

        return Response.<List<EnrolledStudentDTO>>newBuilder()
                .setSuccess(true)
                .setMessage("Fetch list approved student by classId successfully.")
                .setData(enrolledStudentDTOs)
                .build();
    }

    @Override
    public Response<Integer> getNumberWaitingEnrollmentInClass(String request) {
        UUID classId = UUID.fromString(request);

        Integer totalWaitingEnrollment = enrollmentRepository.countWaitingEnrollmentInClass(classId);

        return Response.<Integer>newBuilder()
                .setSuccess(true)
                .setMessage("Get number of total waiting enrollment in class successfully.")
                .setData(totalWaitingEnrollment)
                .build();
    }


}
