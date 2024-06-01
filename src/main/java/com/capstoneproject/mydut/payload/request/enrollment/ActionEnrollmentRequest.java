package com.capstoneproject.mydut.payload.request.enrollment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vndat00
 * @since 5/13/2024
 */

@Getter
@Setter
@AllArgsConstructor
public class ActionEnrollmentRequest {
    private String classId;
    /* 1 is approve, 0 is reject */
    private Integer actionType;
    private List<String> userIds;
}
