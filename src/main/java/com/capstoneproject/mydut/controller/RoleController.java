package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.RoleDTO;
import com.capstoneproject.mydut.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public Response<List<RoleDTO>> getAllRoles() {
        return roleService.getAllRoles();
    }
}
