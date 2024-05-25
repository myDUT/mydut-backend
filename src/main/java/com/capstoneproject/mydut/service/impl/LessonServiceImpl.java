package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.domain.repository.LessonRepository;
import com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.LessonService;
import com.capstoneproject.mydut.util.DateTimeUtils;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    private final SecurityUtils securityUtils;

    @Override
    public Response<List<GeneralInfoLessonDTO>> getLessonsInADay(String time) {
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
}
