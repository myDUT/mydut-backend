package com.capstoneproject.mydut.payload.request.attendancerecord;

import lombok.*;

/**
 * @author vndat00
 * @since 6/3/2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class AttendanceReportRequest {
    private String classId;
    private String lessonId;
}
