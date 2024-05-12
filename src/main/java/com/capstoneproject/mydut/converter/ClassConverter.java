package com.capstoneproject.mydut.converter;

import com.capstoneproject.mydut.domain.entity.ClassEntity;
import com.capstoneproject.mydut.payload.response.ClassDTO;

/**
 * @author vndat00
 * @since 5/12/2024
 */
public class ClassConverter {
    public static ClassDTO map(ClassEntity entity) {
        return ClassDTO.newBuilder()
                .setClassId(String.valueOf(entity.getClassId()))
                .setRoomId(String.valueOf(entity.getRoom().getRoomId()))
                .setRoomName(entity.getRoom().getName())
                .setDayOfWeek(entity.getDayOfWeek())
                .setTimeFrom(entity.getTimeFrom().toString())
                .setTimeTo(entity.getTimeTo().toString())
                .setDateFrom(entity.getDateFrom().toString())
                .setDateTo(entity.getDateTo().toString())
                .setClassCode(entity.getClassCode())
                .build();
    }
}
