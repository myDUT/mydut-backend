package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author vndat00
 * @since 5/24/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/find-lessons-in-date/{time}")
    public Response<List<GeneralInfoLessonDTO>> getAllLessonsInADay(@PathVariable("time") String time) {
        return lessonService.getLessonsInADay(time);
    }
}
