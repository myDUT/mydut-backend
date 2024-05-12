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
public class ApproveEnrollmentRequest {
    private String classId;
    private List<String> userIds;
}
