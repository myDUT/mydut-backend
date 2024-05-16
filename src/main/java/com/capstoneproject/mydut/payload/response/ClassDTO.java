package com.capstoneproject.mydut.payload.response;

import lombok.*;

import java.util.List;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class ClassDTO {
    private String classId;
    private String className;
    private String roomId;
    private String roomName;
    private Integer dayOfWeek;
    private String timeFrom;
    private String timeTo;
    private String dateFrom;
    private String dateTo;
    private Integer totalStudent;
    private String classCode;
    private String lecturer;
    private List<LessonDTO> lessons;
}
