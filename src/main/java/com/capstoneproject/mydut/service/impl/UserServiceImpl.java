package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.common.constants.Constant;
import com.capstoneproject.mydut.domain.entity.RoleEntity;
import com.capstoneproject.mydut.domain.entity.UserEntity;
import com.capstoneproject.mydut.domain.repository.RoleRepository;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import com.capstoneproject.mydut.payload.request.user.NewUserRequest;
import com.capstoneproject.mydut.payload.request.user.UpdateUserRequest;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.payload.response.UserDTO;
import com.capstoneproject.mydut.service.UserService;
import com.capstoneproject.mydut.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.ObjectNotFoundException;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException("username", username));
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>(List.of(new SimpleGrantedAuthority(user.getRole().getRoleName()))));
    }

    @Override
    public Response<OnlyIdDTO> createUser(NewUserRequest request) {
        // TODO: validate request
        UserEntity newUser = new UserEntity();

        newUser.setUsername(RequestUtils.blankIfNull(request.getUsername()));
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
    public Response<OnlyIdDTO> updateUser(UpdateUserRequest request) {
        return null;
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
}
