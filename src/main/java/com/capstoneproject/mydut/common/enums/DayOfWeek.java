package com.capstoneproject.mydut.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * @author vndat00
 * @since 5/9/2024
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DayOfWeek {
    MONDAY(1, "Monday"),
    TUESDAY(2, "Tuesday"),
    WEDNESDAY(3, "Wednesday"),
    THURSDAY(4, "Thursday"),
    FRIDAY(5, "Friday"),
    SATURDAY(6, "Saturday"),
    SUNDAY(7, "Sunday");

    public final Integer idx;
    public final String dayOfWeek;
}
