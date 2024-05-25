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

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public Response<OnlyIdDTO> checkIn(AttendanceRecordRequest request) {
        var principal = securityUtils.getPrincipal();

        AttendanceRecordEntity attendanceRecord = new AttendanceRecordEntity();
        // Get time now
        var currentTimeMillis = System.currentTimeMillis();
        var now = new Timestamp(currentTimeMillis);

        // get lesson
        var lesson = lessonRepository.findById(UUID.fromString(request.getLessonId())).orElseThrow(() ->
                new ObjectNotFoundException("lessonId", request.getLessonId()));
        var landmarkCoordinateEntity = lesson.getCoordinate();

        if (Boolean.FALSE.equals(lesson.getIsEnableCheckIn())) {
            return Response.<OnlyIdDTO>newBuilder()
                    .setSuccess(false)
                    .setMessage("Not accepted check-in this time.")
                    .build();
        }

        // get user
        var user = userRepository.findById(UUID.fromString(principal.getUserId())).orElseThrow(() ->
                new ObjectNotFoundException("userId", principal.getUserId()));

        // create coordinate
        CoordinateEntity coordinate = new CoordinateEntity();
        coordinate.setLongitude(request.getCoordinate().getLongitude());
        coordinate.setLatitude(request.getCoordinate().getLatitude());
        var createdCoordinate =  coordinateRepository.save(coordinate);

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

        attendanceRecord.setUser(user);
        attendanceRecord.setCoordinate(createdCoordinate);
        attendanceRecord.setLesson(lesson);
        attendanceRecord.setTimeIn(now);
        attendanceRecord.setIsFacialRecognition(Boolean.FALSE);
        attendanceRecord.setDistance(distance);
        attendanceRecord.setIsValidCheckIn(isValidCheckIn);

        var createdAttendanceRecord = attendanceRecordRepository.save(attendanceRecord);

        return Response.<OnlyIdDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Check in successfully.")
                .setData(OnlyIdDTO.newBuilder()
                        .setId(createdAttendanceRecord.getAttendanceRecordId().toString())
                        .build())
                .build();
    }
}
