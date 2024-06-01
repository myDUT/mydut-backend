package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.common.constants.Constant;
import com.capstoneproject.mydut.domain.entity.RoleEntity;
import com.capstoneproject.mydut.domain.entity.UserEntity;
import com.capstoneproject.mydut.domain.repository.RoleRepository;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import com.capstoneproject.mydut.exception.ObjectNotFoundException;
import com.capstoneproject.mydut.payload.request.user.NewUserRequest;
import com.capstoneproject.mydut.payload.request.user.UpdateUserRequest;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.UserDTO;
import com.capstoneproject.mydut.service.UserService;
import com.capstoneproject.mydut.util.RequestUtils;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("username", username));
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>(List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()))));
    }

    @Override
    public Response<OnlyIdDTO> createUser(NewUserRequest request) {
        // TODO: validate request
        UserEntity newUser = new UserEntity();

        newUser.setLowerUsername(RequestUtils.blankIfNull(request.getUsername()));
        if (StringUtils.isNotBlank(request.getFullName())) {
            newUser.setFullName(request.getFullName());
        }
        if (StringUtils.isNotBlank(request.getEmail())) {
            newUser.setEmail(request.getEmail());
        }
        newUser.setStudentCode(RequestUtils.blankIfNull(request.getStudentCode()));
        newUser.setHomeroomClass(RequestUtils.blankIfNull(request.getHomeroomClass()));
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        RoleEntity role = roleRepository.findById(UUID.fromString(request.getRoleId())).orElseThrow(() ->
                new ObjectNotFoundException("roleId", request.getRoleId()));
        newUser.setRole(role);

        newUser.prePersist(Constant.defaultId);

        var addedUser = userRepository.save(newUser);

        return Response.<OnlyIdDTO>newBuilder()
                .setSuccess(true)
                .setData(OnlyIdDTO.newBuilder()
                        .setId(String.valueOf(addedUser.getUserId()))
                        .build())
                .build();
    }

    @Override
    public Response<OnlyIdDTO> updateUser(String userId, UpdateUserRequest request) {
        // TODO: validate updateUser request
        var principal = securityUtils.getPrincipal();

        if (!principal.getUserId().equalsIgnoreCase(userId) && !Boolean.TRUE.equals(principal.isAdmin())) {
            return Response.<OnlyIdDTO>newBuilder()
                    .setSuccess(false)
                    .setMessage("No permit")
                    .build();
        }

        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() ->
                new ObjectNotFoundException("userId", userId));

        if (StringUtils.isNotBlank(request.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (StringUtils.isNotBlank(request.getStudentCode())) {
            user.setStudentCode(request.getStudentCode());
        }
        if (StringUtils.isNotBlank(request.getHomeroomClass())) {
            user.setHomeroomClass(request.getHomeroomClass());
        }
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());

        userRepository.save(user);

        return Response.<OnlyIdDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Update user information successfully.")
                .setData(OnlyIdDTO.newBuilder()
                        .setId(userId)
                        .build())
                .build();
    }

    @Override
    public Response<UserDTO> getUserById(String userId) {
        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() ->
                new ObjectNotFoundException("userId", userId));

        return Response.<UserDTO>newBuilder()
                .setSuccess(true)
                .setData(UserDTO.newBuilder()
                        .setUserId(String.valueOf(user.getUserId()))
                        .setUsername(user.getUsername())
                        .setFullName(user.getFullName())
                        .setStudentCode(user.getStudentCode())
                        .setHomeroomClass(user.getHomeroomClass())
                        .setEmail(user.getEmail())
                        .setRoleId(String.valueOf(user.getRole().getRoleId()))
                        .setRoleName(user.getRole().getRoleName())
                        .build()
                )
                .build();

    }

    @Override
    public Response<NoContentDTO> deleteUser(String userId) {
        var principal = securityUtils.getPrincipal();

        if (!principal.getUserId().equalsIgnoreCase(userId) && !Boolean.TRUE.equals(principal.isAdmin())) {
            return Response.<NoContentDTO>newBuilder()
                    .setSuccess(false)
                    .setMessage("No permit")
                    .build();
        }
        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() ->
                new ObjectNotFoundException("userId", userId));

        userRepository.delete(user);

        return Response.<NoContentDTO>newBuilder()
                .setSuccess(true)
                .setData(NoContentDTO.newBuilder().build())
                .build();
    }
}
