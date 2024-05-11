package com.capstoneproject.mydut.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author vndat00
 * @since 5/9/2024
 */

@Getter
@AllArgsConstructor
public enum EnrollmentStatus {
    WAITING("waiting"),
    REJECTED("rejected"),
    APPROVED("approved");

    public final String value;
}
