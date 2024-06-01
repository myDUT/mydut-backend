package com.capstoneproject.mydut.converter;

import com.capstoneproject.mydut.domain.projection.EnrolledStudent;
import com.capstoneproject.mydut.payload.response.EnrolledStudentDTO;

/**
 * @author vndat00
 * @since 6/1/2024
 */
public class EnrollmentConverter {
    private EnrollmentConverter() {}
    public static EnrolledStudentDTO.EnrolledStudentDTOBuilder projection2Dto(EnrolledStudent projection) {
        return EnrolledStudentDTO.newBuilder()
                .setEnrollmentId(projection.getEnrollmentId().toString())
                .setUserId(projection.getUserId().toString())
                .setUsername(projection.getUsername())
                .setFullName(projection.getFullName())
                .setStudentCode(projection.getStudentCode())
                .setHomeroomClass(projection.getHomeroomClass())
                .setEmail(projection.getEmail())
                .setClassId(projection.getClassId().toString())
                .setClassName(projection.getClassName())
                .setStatusEnrollment(projection.getStatus());
    }
}
