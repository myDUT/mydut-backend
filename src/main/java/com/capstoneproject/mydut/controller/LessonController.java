package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.payload.request.lesson.StartCheckInRequest;
import com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/start-check-in")
    @Secured(value = {
            MyDUTPermission.Role.TEACHER
    })
    public Response<NoContentDTO> startCheckIn(@RequestBody StartCheckInRequest startCheckInDTO) {
        return lessonService.startCheckIn(startCheckInDTO);
    }

    @PostMapping("/end-check-in")
    @Secured(value = {
            MyDUTPermission.Role.TEACHER
    })
    public Response<NoContentDTO> endCheckIn(@RequestParam(value = "lessonId") String lessonId) {
        return lessonService.endCheckIn(lessonId);
    }
}
