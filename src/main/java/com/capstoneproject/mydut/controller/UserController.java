package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.payload.request.user.NewUserRequest;
import com.capstoneproject.mydut.payload.request.user.UpdateUserRequest;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.UserDTO;
import com.capstoneproject.mydut.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/add-new-user")
    public Response<OnlyIdDTO> addNewUser(@RequestBody NewUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("{id}")
    public Response<UserDTO> getUserById(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    @PutMapping("{id}")
    public Response<OnlyIdDTO> updateUser(@PathVariable("id") String userId, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("{id}")
    public Response<NoContentDTO> deleteUser(@PathVariable("id") String userId) {
        return userService.deleteUser(userId);
    }
}
