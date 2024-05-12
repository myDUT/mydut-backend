package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.RoomDTO;
import com.capstoneproject.mydut.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public Response<List<RoomDTO>> getAllRooms() {
        return roomService.getAllRooms();
    }
}
