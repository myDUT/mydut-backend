package com.capstoneproject.mydut.payload.request.user;

import lombok.*;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String fullName;
    private String password;
    private String studentCode;
    private String homeroomClass;
    private String email;
}
