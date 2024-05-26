package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.lesson.StartCheckInRequest;
import com.capstoneproject.mydut.payload.response.*;

import java.util.List;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface LessonService {
    Response<List<GeneralInfoLessonDTO>> getAllLessonsInADay(String time);
    Response<NoContentDTO> startCheckIn(StartCheckInRequest request);
    Response<NoContentDTO> endCheckIn(String lessonId);
    Response<ListDTO<LessonDTO>> getAllLessonsByClassId(String classId);
    void updatePresentStudentInLesson(String lessonId);
}
