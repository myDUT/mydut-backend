package com.capstoneproject.mydut.exception;

import com.capstoneproject.mydut.payload.response.ErrorDTO;
import lombok.Getter;

import java.util.List;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@Getter
public class ValidateException extends RuntimeException{
    private final String message = "Validate failure";
    private final List<ErrorDTO> errors;

    public ValidateException(List<ErrorDTO> errors){
        super();
        this.errors = errors;
    }
}
