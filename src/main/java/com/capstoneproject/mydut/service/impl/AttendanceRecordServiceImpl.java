package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.domain.dto.CoordinateDTO;
import com.capstoneproject.mydut.domain.entity.AttendanceRecordEntity;
import com.capstoneproject.mydut.domain.entity.CoordinateEntity;
import com.capstoneproject.mydut.domain.repository.AttendanceRecordRepository;
import com.capstoneproject.mydut.domain.repository.CoordinateRepository;
import com.capstoneproject.mydut.domain.repository.LessonRepository;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import com.capstoneproject.mydut.exception.ObjectNotFoundException;
import com.capstoneproject.mydut.payload.request.attendancerecord.AttendanceRecordRequest;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.AttendanceRecordService;
import com.capstoneproject.mydut.service.LessonService;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.UUID;

import static com.capstoneproject.mydut.common.constants.Constant.VALID_DISTANCE_CHECK_IN;
import static com.capstoneproject.mydut.util.CoordinateUtils.calcDistance;

/**
 * @author vndat00
 * @since 5/26/2024
 */

@Service
@RequiredArgsConstructor
public class AttendanceRecordServiceImpl implements AttendanceRecordService {
    private final LessonRepository lessonRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final CoordinateRepository coordinateRepository;
    private final UserRepository userRepository;

    private final LessonService lessonService;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public Response<OnlyIdDTO> checkIn(AttendanceRecordRequest request) {
        var principal = securityUtils.getPrincipal();

        // Get time now
        var currentTimeMillis = System.currentTimeMillis();
        var now = new Timestamp(currentTimeMillis);

        // get lesson
        var lesson = lessonRepository.findById(UUID.fromString(request.getLessonId())).orElseThrow(() ->
                new ObjectNotFoundException("lessonId", request.getLessonId()));
        var landmarkCoordinateEntity = lesson.getCoordinate();

        if (Boolean.FALSE.equals(lesson.getIsEnableCheckIn()) || now.before(lesson.getDatetimeFrom()) || now.after(lesson.getDatetimeTo())) {
            return Response.<OnlyIdDTO>newBuilder()
                    .setSuccess(false)
                    .setMessage("Not accepted check-in this time.")
                    .build();
        }

        CoordinateDTO studentCoordinate = CoordinateDTO.newBuilder()
                .setLatitude(request.getCoordinate().getLatitude())
                .setLongitude(request.getCoordinate().getLongitude())
                .build();

        CoordinateDTO landmarkCoordinate = CoordinateDTO.newBuilder()
                .setLatitude(landmarkCoordinateEntity.getLatitude())
                .setLongitude(landmarkCoordinateEntity.getLongitude())
                .build();

        var distance = calcDistance(studentCoordinate, landmarkCoordinate);

        var isValidCheckIn = distance <= VALID_DISTANCE_CHECK_IN;

        // create coordinate
        CoordinateEntity coordinate = new CoordinateEntity();
        coordinate.setLongitude(request.getCoordinate().getLongitude());
        coordinate.setLatitude(request.getCoordinate().getLatitude());
        var createdCoordinate = coordinateRepository.save(coordinate);

        var existAttendanceRecord = attendanceRecordRepository.findByUserIdAndLessonId(UUID.fromString(principal.getUserId()), UUID.fromString(request.getLessonId()));

        if (existAttendanceRecord.isPresent()) {
            var updatedRecord = existAttendanceRecord.get();
            updatedRecord.setCoordinate(createdCoordinate);
            updatedRecord.setTimeIn(now);
            updatedRecord.setDistance(distance);
            updatedRecord.setIsValidCheckIn(isValidCheckIn);

            attendanceRecordRepository.save(updatedRecord);

            // update presentStudent/absentStudent after checkin
            lessonService.updatePresentStudentInLesson(request.getLessonId());

            return Response.<OnlyIdDTO>newBuilder()
                    .setSuccess(true)
                    .setMessage("Update check in record successfully.")
                    .setData(OnlyIdDTO.newBuilder()
                            .setId(updatedRecord.getAttendanceRecordId().toString())
                            .build())
                    .build();
        }

        AttendanceRecordEntity newAttendanceRecord = new AttendanceRecordEntity();

        // get user
        var user = userRepository.findById(UUID.fromString(principal.getUserId())).orElseThrow(() ->
                new ObjectNotFoundException("userId", principal.getUserId()));

        newAttendanceRecord.setUser(user);
        newAttendanceRecord.setCoordinate(createdCoordinate);
        newAttendanceRecord.setLesson(lesson);
        newAttendanceRecord.setTimeIn(now);
        newAttendanceRecord.setIsFacialRecognition(Boolean.FALSE);
        newAttendanceRecord.setDistance(distance);
        newAttendanceRecord.setIsValidCheckIn(isValidCheckIn);

        var createdAttendanceRecord = attendanceRecordRepository.save(newAttendanceRecord);

        // update presentStudent/absentStudent after checkin
        lessonService.updatePresentStudentInLesson(request.getLessonId());

        return Response.<OnlyIdDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Create new check-in record successfully.")
                .setData(OnlyIdDTO.newBuilder()
                        .setId(createdAttendanceRecord.getAttendanceRecordId().toString())
                        .build())
                .build();
    }
}
