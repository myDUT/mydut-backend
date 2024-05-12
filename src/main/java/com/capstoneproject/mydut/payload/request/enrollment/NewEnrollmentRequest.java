package com.capstoneproject.mydut.payload.request.enrollment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vndat00
 * @since 5/13/2024
 */

@Getter
@Setter
@NoArgsConstructor
public class NewEnrollmentRequest {
    private String classCode;
}
