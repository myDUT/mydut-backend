package com.capstoneproject.mydut.payload.response;

import com.capstoneproject.mydut.common.enums.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author vndat00
 * @since 5/9/2024
 */

@Getter
@Setter
@Builder(builderMethodName = "newBuilder", setterPrefix = "set")
public class Response<T> implements Serializable {
    private Boolean success;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private ErrorCode errorCode;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ErrorDTO> errors;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String exception;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private T data;
}
