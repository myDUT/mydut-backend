package com.capstoneproject.mydut.payload.request.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Getter
@Setter
@NoArgsConstructor
public class NewUserRequest {
    private String username;
    private String fullName;
    private String password;
    private String studentCode;
    private String homeroomClass;
    private String roleId;
    private String email;
}
