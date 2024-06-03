package com.capstoneproject.mydut.payload.response;

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
public class AttendanceRecordDTO {
    private String userId;
    private String fullName;
    private String studentCode;
    private String homeroomClass;
    private String timeIn;
    private Boolean isValidCheckIn;
    private Boolean isFacialRecognition;
    private Double distance;
}
