package com.capstoneproject.mydut.controller;


import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.payload.request.enrollment.ActionEnrollmentRequest;
import com.capstoneproject.mydut.payload.request.enrollment.NewEnrollmentRequest;
import com.capstoneproject.mydut.payload.response.*;
import com.capstoneproject.mydut.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author vndat00
 * @since 5/13/2024
 */


@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    @PostMapping
    @Secured(value = {
            MyDUTPermission.Role.STUDENT
    })
    public Response<OnlyIdDTO> createEnrollment(@RequestBody NewEnrollmentRequest request) {
        return enrollmentService.createEnrollment(request);
    }

    @PostMapping("/action-enrollment")
    @Secured(value = {
            MyDUTPermission.Role.TEACHER
    })
    public Response<NoContentDTO> actionEnrollment(@RequestBody ActionEnrollmentRequest request) {
        return enrollmentService.actionEnrollment(request);
    }
    @GetMapping("/enrolled-student")
    public Response<List<EnrolledStudentDTO>> getAllEnrolledStudentInClass(@RequestParam(name = "classId") String request) {
        return enrollmentService.getAllEnrolledStudentByClassId(request);
    }

    @GetMapping("/waiting-enrollment")
    public Response<Integer> getNumberWaitingEnrollmentInClass(@RequestParam(name = "classId") String request) {
        return enrollmentService.getNumberWaitingEnrollmentInClass(request);
    }
}
