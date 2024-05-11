package com.capstoneproject.mydut.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * @author vndat00
 * @since 5/11/2024
 */
public class RequestUtils {
    public static String blankIfNull(@Nullable String value) {
        return StringUtils.hasText(value) ? value : "";
    }
}
