package com.capstoneproject.mydut.common;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import lombok.*;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "newBuilder", setterPrefix = "set")
public class MyDUTPrincipal {
    private String userId;
    private String username;
    private String fullName;
    private String email;
    private String role;
    private String roleId;
    private String studentCode;
    private String homeroomClass;

    public Boolean isAdmin() {
        return role.equalsIgnoreCase(MyDUTPermission.Role.ADMIN);
    }
    public Boolean isTeacher() {
        return role.equalsIgnoreCase(MyDUTPermission.Role.TEACHER);
    }
    public Boolean isStudent() {
        return role.equalsIgnoreCase(MyDUTPermission.Role.STUDENT);
    }
}
