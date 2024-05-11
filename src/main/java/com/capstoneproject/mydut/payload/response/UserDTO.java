package com.capstoneproject.mydut.payload.response;

import lombok.*;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class UserDTO {
    private String userId;
    private String username;
    private String fullName;
    private String studentCode;
    private String homeroomClass;
    private String roleId;
    private String roleName;
    private String email;
}
