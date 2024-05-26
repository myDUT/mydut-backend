package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.domain.entity.CoordinateEntity;
import com.capstoneproject.mydut.domain.repository.AttendanceRecordRepository;
import com.capstoneproject.mydut.domain.repository.CoordinateRepository;
import com.capstoneproject.mydut.domain.repository.LessonRepository;
import com.capstoneproject.mydut.exception.ObjectNotFoundException;
import com.capstoneproject.mydut.payload.request.lesson.StartCheckInRequest;
import com.capstoneproject.mydut.payload.response.*;
import com.capstoneproject.mydut.service.LessonService;
import com.capstoneproject.mydut.util.DateTimeUtils;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/24/2024
 */

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CoordinateRepository coordinateRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final SecurityUtils securityUtils;

    @Override
    public Response<List<GeneralInfoLessonDTO>> getAllLessonsInADay(String time) {
        var principal = securityUtils.getPrincipal();
        List<GeneralInfoLessonDTO> listLessons = new ArrayList<>();

        String role = principal.getRole();
        String userId = principal.getUserId();

        // Parse time to time-begin and time-end of a day
        Date startTime = DateTimeUtils.move2BeginTimeOfDay(DateTimeUtils.string2Timestamp(time));
        Date endTime = DateTimeUtils.move2EndTimeOfDay(DateTimeUtils.string2Timestamp(time));

        Timestamp start = new Timestamp(startTime.getTime());
        Timestamp end = new Timestamp(endTime.getTime());

        if (MyDUTPermission.Role.ADMIN.equalsIgnoreCase(role)) {
            listLessons = lessonRepository.findAllLessonsInOneDay(start, end);
        } else if (MyDUTPermission.Role.TEACHER.equalsIgnoreCase(role)) {
            listLessons = lessonRepository.findAllLessonsCreatedByTeacherInOneDay(UUID.fromString(userId), start, end);
        } else if (MyDUTPermission.Role.STUDENT.equalsIgnoreCase(role)) {
            listLessons = lessonRepository.findAllLessonsBelongToStudentInOneDay(UUID.fromString(userId), start, end);
        }

        return Response.<List<GeneralInfoLessonDTO>>newBuilder()
                .setSuccess(true)
                .setMessage("Get list lessons successfully.")
                .setData(listLessons)
                .build();
    }

    @Override
    @Transactional
    public Response<NoContentDTO> startCheckIn(StartCheckInRequest request) {
        var lesson = lessonRepository.findById(UUID.fromString(request.getLessonId())).orElseThrow(() ->
                new ObjectNotFoundException("lessonId", request.getLessonId()));

        // create coordinate entity
        CoordinateEntity coordinate = new CoordinateEntity();
        coordinate.setLatitude(request.getCoordinate().getLatitude());
        coordinate.setLongitude(request.getCoordinate().getLongitude());

        var createdCoordinate = coordinateRepository.save(coordinate);

        if (Boolean.FALSE.equals(lesson.getIsEnableCheckIn())) {
            lesson.setIsEnableCheckIn(Boolean.TRUE);
        }
        lesson.setCoordinate(createdCoordinate);

        lessonRepository.save(lesson);

        return Response.<NoContentDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Start check-in successfully.")
                .build();
    }

    @Override
    @Transactional
    public Response<NoContentDTO> endCheckIn(String lessonId) {
        var lesson = lessonRepository.findById(UUID.fromString(lessonId)).orElseThrow(() ->
                new ObjectNotFoundException("lessonId", lessonId));

        if (Boolean.TRUE.equals(lesson.getIsEnableCheckIn())) {
            lesson.setIsEnableCheckIn(Boolean.FALSE);
        }
        lessonRepository.save(lesson);
        return Response.<NoContentDTO>newBuilder()
                .setSuccess(true)
                .setMessage("End check in successfully.")
                .build();
    }

    @Override
    public Response<ListDTO<LessonDTO>> getAllLessonsByClassId(String classId) {
        return null;
    }

    @Override
    @Transactional
    public void updatePresentStudentInLesson(String lessonId) {
        var lesson = lessonRepository.findById(UUID.fromString(lessonId)).orElseThrow(() ->
                new ObjectNotFoundException("lessonId", lessonId));

        Integer presentStudent = attendanceRecordRepository.countValidCheckInByLessonId(UUID.fromString(lessonId));

        lesson.setPresentStudent(presentStudent);

        lessonRepository.save(lesson);
    }
}
