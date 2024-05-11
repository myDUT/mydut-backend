package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.user.NewUserRequest;
import com.capstoneproject.mydut.payload.request.user.UpdateUserRequest;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.UserDTO;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface UserService {
    Response<OnlyIdDTO> createUser(NewUserRequest request);
    Response<OnlyIdDTO> updateUser(String userId, UpdateUserRequest request);
    Response<UserDTO> getUserById(String userId);

}
