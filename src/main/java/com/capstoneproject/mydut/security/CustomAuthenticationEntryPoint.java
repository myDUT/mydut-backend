package com.capstoneproject.mydut.security;

import com.capstoneproject.mydut.common.enums.ErrorCode;
import com.capstoneproject.mydut.payload.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.error("Unauthorized: {}", request.getRequestURI());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        var error = Response.newBuilder()
                .setSuccess(false)
                .setMessage(authException.getMessage())
                .setErrorCode(ErrorCode.UNAUTHORIZED)
                .setException(authException.getClass().getSimpleName())
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
