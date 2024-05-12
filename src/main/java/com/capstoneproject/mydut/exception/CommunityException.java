package com.capstoneproject.mydut.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@AllArgsConstructor
@Getter
public class CommunityException extends RuntimeException {
    protected final boolean isClientError;
}
