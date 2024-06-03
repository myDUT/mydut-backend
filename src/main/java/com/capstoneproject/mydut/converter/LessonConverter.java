package com.capstoneproject.mydut.converter;

import com.capstoneproject.mydut.domain.projection.LessonDetail;
import com.capstoneproject.mydut.payload.response.LessonDTO;

public class LessonConverter {
    public static LessonDTO.LessonDTOBuilder projection2Dto(LessonDetail projection) {
        return LessonDTO.newBuilder()
                .setLessonId(projection.getLessonId().toString())
                .setClassId(projection.getClassId().toString())
                .setClassName(projection.getClassName())
                .setDatetimeFrom(projection.getDatetimeFrom().toString())
                .setDatetimeTo(projection.getDatetimeTo().toString())
                .setTotalStudent(projection.getTotalStudent())
                .setPresentStudent(projection.getPresentStudent())
                .setRoomName(projection.getRoomName());
    }
}
