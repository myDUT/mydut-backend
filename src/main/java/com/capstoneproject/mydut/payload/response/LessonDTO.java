package com.capstoneproject.mydut.payload.response;

import lombok.*;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class LessonDTO {
    private String lessonId;
    private String classId;
    private String className;
    private String datetimeFrom;
    private String datetimeTo;
    private String roomName;
    private String description;
    private Integer totalStudent;
    private Integer presentStudent;
    private Integer absentStudent;
}
