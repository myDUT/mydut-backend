package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.RoleDTO;

import java.util.List;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface RoleService {
    Response<List<RoleDTO>> getAllRoles();
}
