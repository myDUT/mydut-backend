package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.RoomDTO;

import java.util.List;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface RoomService {
    Response<List<RoomDTO>> getAllRooms();
}
