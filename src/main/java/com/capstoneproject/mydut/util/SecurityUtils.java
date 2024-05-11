package com.capstoneproject.mydut.util;

import com.capstoneproject.mydut.common.MyDUTPrincipal;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final UserRepository userRepository;

    public MyDUTPrincipal getPrincipal() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            var principal = userRepository.findByUsername(username).get();
            return MyDUTPrincipal.newBuilder()
                    .setUserId(String.valueOf(principal.getUserId()))
                    .setUsername(principal.getUsername())
                    .setFullName(StringUtils.defaultString(principal.getFullName()))
                    .setStudentCode(principal.getStudentCode())
                    .setEmail(StringUtils.defaultString(principal.getEmail()))
                    .setHomeroomClass(principal.getHomeroomClass())
                    .setRole(principal.getRole().getRoleName())
                    .setRoleId(String.valueOf(principal.getRole().getRoleId()))
                    .build();
        }
        return null;
    }
}
