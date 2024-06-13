package com.capstoneproject.mydut.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author vndat00
 * @since 6/9/2024
 */

@Getter
@AllArgsConstructor
public enum PreSignedUrlType {
    PRE_SIGNED_URL_TYPE_UNSPECIFIED(0, "UNKNOWN"),
    PRE_SIGNED_URL_TYPE_UPLOAD(1, "PUT"),
    PRE_SIGNED_URL_TYPE_DOWNLOAD(2, "GET");

    public final Integer id;
    public final String value;
}
