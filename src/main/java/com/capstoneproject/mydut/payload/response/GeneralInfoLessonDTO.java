package com.capstoneproject.mydut.payload.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/24/2024
 */



@Getter
@Setter
//@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class GeneralInfoLessonDTO {
    private UUID classId;
    private UUID lessonId;
    private String className;
    private String classCode;
    private UUID roomId;
    private String roomName;
    private Date datetimeFrom;
    private Date datetimeTo;
    private String lecturer;
    private Integer totalStudent;
    private Integer presentStudent;

    public GeneralInfoLessonDTO(UUID classId, UUID lessonId, String className, String classCode,
                                UUID roomId, String roomName, Date datetimeFrom, Date datetimeTo,
                                String lecturer, Integer totalStudent, Integer presentStudent) {
        this.classId = classId;
        this.lessonId = lessonId;
        this.className = className;
        this.classCode = classCode;
        this.roomId = roomId;
        this.roomName = roomName;
        this.datetimeFrom = datetimeFrom;
        this.datetimeTo = datetimeTo;
        this.lecturer = lecturer;
        this.totalStudent = totalStudent;
        this.presentStudent = presentStudent;
    }
}
