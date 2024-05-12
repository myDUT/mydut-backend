package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.domain.repository.RoomRepository;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.RoomDTO;
import com.capstoneproject.mydut.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public Response<List<RoomDTO>> getAllRooms() {
        var rooms = roomRepository.findAll();
        return Response.<List<RoomDTO>>newBuilder()
                .setSuccess(true)
                .setMessage("Get list rooms successfully.")
                .setData(rooms.stream()
                        .map(r -> RoomDTO.newBuilder()
                                .setRoomId(String.valueOf(r.getRoomId()))
                                .setName(r.getName())
                                .build()
                        )
                        .collect(Collectors.toList()))
                .build();
    }
}
