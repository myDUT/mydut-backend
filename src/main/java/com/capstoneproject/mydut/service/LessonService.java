package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.lesson.StartCheckInRequest;
import com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.Response;

import java.util.List;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface LessonService {
    Response<List<GeneralInfoLessonDTO>> getLessonsInADay(String time);
    Response<NoContentDTO> startCheckIn(StartCheckInRequest request);
    Response<NoContentDTO> endCheckIn(String lessonId);
}
