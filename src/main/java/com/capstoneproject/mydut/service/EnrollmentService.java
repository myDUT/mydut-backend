package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.enrollment.ActionEnrollmentRequest;
import com.capstoneproject.mydut.payload.request.enrollment.NewEnrollmentRequest;
import com.capstoneproject.mydut.payload.response.EnrolledStudentDTO;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;

import java.util.List;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface EnrollmentService {
    Response<OnlyIdDTO> createEnrollment(NewEnrollmentRequest request);
    Response<NoContentDTO> actionEnrollment(ActionEnrollmentRequest request);
    Response<List<EnrolledStudentDTO>> getAllEnrolledStudentByClassId(String request);
    Response<Integer> getNumberWaitingEnrollmentInClass(String request);
}
