package com.capstoneproject.mydut.controller;


import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.payload.request.enrollment.ApproveEnrollmentRequest;
import com.capstoneproject.mydut.payload.request.enrollment.NewEnrollmentRequest;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/approve-user")
    @Secured(value = {
            MyDUTPermission.Role.TEACHER
    })
    public Response<NoContentDTO> approveEnrollment(@RequestBody ApproveEnrollmentRequest request) {
        return enrollmentService.approveEnrollment(request);
    }
}
