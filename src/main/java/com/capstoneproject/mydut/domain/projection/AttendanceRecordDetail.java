package com.capstoneproject.mydut.domain.projection;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author vndat00
 * @since 6/3/2024
 */
public interface AttendanceRecordDetail {
    UUID attendanceRecordId();
    UUID getUserId();
    UUID getLessonId();
    Timestamp getTimeIn();
    Boolean getIsValidCheckIn();
    Boolean getIsFacialRecognition();
    Double getDistance();

}
