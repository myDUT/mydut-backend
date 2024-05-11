package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.domain.repository.RoleRepository;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.RoleDTO;
import com.capstoneproject.mydut.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Response<List<RoleDTO>> getAllRoles() {
        var roles = roleRepository.findAll();
        return Response.<List<RoleDTO>>newBuilder()
                .setSuccess(true)
                .setData(roles.stream()
                        .map(r -> RoleDTO.newBuilder()
                                .setRoleId(String.valueOf(r.getRoleId()))
                                .setRoleName(r.getRoleName())
                                .setDescription(r.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
