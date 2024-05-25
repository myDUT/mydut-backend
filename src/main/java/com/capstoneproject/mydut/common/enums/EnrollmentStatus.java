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
    REJECTED(0, "rejected"),
    APPROVED(1, "approved"),
    WAITING(2, "waiting");

    public final int id;
    public final String value;
}
