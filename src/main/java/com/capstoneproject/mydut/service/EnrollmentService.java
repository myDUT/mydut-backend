package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.enrollment.ApproveEnrollmentRequest;
import com.capstoneproject.mydut.payload.request.enrollment.NewEnrollmentRequest;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface EnrollmentService {
    Response<OnlyIdDTO> createEnrollment(NewEnrollmentRequest request);
    Response<NoContentDTO> approveEnrollment(ApproveEnrollmentRequest request);
}
