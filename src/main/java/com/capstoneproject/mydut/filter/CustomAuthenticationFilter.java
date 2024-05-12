package com.capstoneproject.mydut.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.capstoneproject.mydut.common.constants.Constant;
import com.capstoneproject.mydut.common.enums.ErrorCode;
import com.capstoneproject.mydut.domain.repository.UserRepository;
import com.capstoneproject.mydut.exception.ObjectNotFoundException;
import com.capstoneproject.mydut.payload.response.LoginSuccessResponse;
import com.capstoneproject.mydut.payload.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final long EXPIRE_DURATION_ACCESS_TOKEN = 2L * 60 * 60 * 1000; // 1h
    private static final long EXPIRE_DURATION_REFRESH_TOKEN = 10L * 60 * 60 * 1000; // 10hrs

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authToken);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        var account = userRepository.findByUsername(user.getUsername()).orElseThrow(() ->
                new ObjectNotFoundException("username", user.getUsername()));
        Algorithm algorithm = Algorithm.HMAC256(Constant.BYTE_CODE.getBytes());
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_DURATION_ACCESS_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().findFirst().get().getAuthority())
                .withClaim("account_id", String.valueOf(account.getUserId()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_DURATION_REFRESH_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        var res = Response.newBuilder().setSuccess(true)
                .setData(LoginSuccessResponse.builder()
                        .setAccessToken(accessToken)
                        .setRefreshToken(refreshToken)
                        .setFullName(account.getFullName())
                        .setUsername(account.getUsername())
                        .setRoleName(account.getRole().getRoleName())
                        .setUserId(String.valueOf(account.getUserId()))
                        .setRoleId(String.valueOf(account.getRole().getRoleId()))
                        .newBuilder())
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), res);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Response<Object> responseObj = Response.<Object>newBuilder()
                .setSuccess(false)
                .setMessage(failed.getMessage())
                .setErrorCode(ErrorCode.AUTHENTICATION_FAILURE)
                .setException(failed.getClass().getSimpleName())
                .build();
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        new ObjectMapper().writeValue(response.getOutputStream(), responseObj);
    }
}
