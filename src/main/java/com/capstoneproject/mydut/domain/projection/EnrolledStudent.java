package com.capstoneproject.mydut.domain.projection;

import java.util.UUID;

/**
 * @author vndat00
 * @since 6/1/2024
 */
public interface EnrolledStudent {
    UUID getEnrollmentId();
    UUID getUserId();
    String getUsername();
    String getFullName();
    String getStudentCode();
    String getHomeroomClass();
    String getEmail();
    UUID getClassId();
    String getClassName();
    Integer getStatus();


}
