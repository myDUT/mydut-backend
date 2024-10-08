package com.capstoneproject.mydut.domain.projection;

import java.sql.Timestamp;
import java.util.UUID;

public interface LessonDetail {
    UUID getLessonId();
    UUID getClassId();
    String getRoomName();
    Timestamp getDatetimeFrom();
    Timestamp getDatetimeTo();
    Integer getPresentStudent();
    String getClassName();
    Integer getTotalStudent();


}
