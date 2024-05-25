package com.capstoneproject.mydut.payload.request.attendancerecord;

import com.capstoneproject.mydut.domain.dto.CoordinateDTO;
import lombok.*;

/**
 * @author vndat00
 * @since 5/26/2024
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class AttendanceRecordRequest {
    private String lessonId;
    private CoordinateDTO coordinate;
}
