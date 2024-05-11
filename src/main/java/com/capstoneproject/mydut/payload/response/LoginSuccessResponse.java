package com.capstoneproject.mydut.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Getter
@Setter
@Builder(buildMethodName = "newBuilder", setterPrefix = "set")
public class LoginSuccessResponse {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String fullName;
    private String roleName;
    private String userId;
    private String roleId;
}
