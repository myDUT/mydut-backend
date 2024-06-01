package com.capstoneproject.mydut.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vndat00
 * @since 5/31/2024
 */

@Getter
@Setter
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class EnrolledStudentDTO {
    private String enrollmentId;
    private String userId;
    private String username;
    private String fullName;
    private String studentCode;
    private String homeroomClass;
    private String email;
    private String classId;
    private String className;
    private Integer statusEnrollment;
}
