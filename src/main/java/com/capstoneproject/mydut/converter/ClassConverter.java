package com.capstoneproject.mydut.converter;

import com.capstoneproject.mydut.domain.entity.ClassEntity;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import com.capstoneproject.mydut.payload.response.ClassDTO;
import lombok.RequiredArgsConstructor;

/**
 * @author vndat00
 * @since 5/12/2024
 */
public class ClassConverter {
    public static ClassDTO.ClassDTOBuilder entityToBuilder(ClassEntity entity) {
        return ClassDTO.newBuilder()
                .setClassId(String.valueOf(entity.getClassId()))
                .setClassName(entity.getName())
                .setRoomId(String.valueOf(entity.getRoom().getRoomId()))
                .setRoomName(entity.getRoom().getName())
                .setDayOfWeek(entity.getDayOfWeek())
                .setTimeFrom(entity.getTimeFrom().toString())
                .setTimeTo(entity.getTimeTo().toString())
                .setDateFrom(entity.getDateFrom().toString())
                .setDateTo(entity.getDateTo().toString())
                .setClassCode(entity.getClassCode())
                .setTotalStudent(entity.getTotalStudent());
    }

    public static ClassDTO entityToDTO(ClassEntity entity) {
        return entityToBuilder(entity).build();
    }
}
