package com.capstoneproject.mydut.common.constants;

import java.util.List;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/11/2024
 */

public class Constant {
    public static final UUID defaultId = UUID.fromString("00000000-0000-0000-0000-000000000000");

    // Authentication
    public final static String PREFIX_TOKEN = "Bearer ";
    public final static String BYTE_CODE = "m1dut2024";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String LOGIN_PATH = "/login";
    public static final String REFRESH_TOKEN_PATH = "/token/refresh";
    public static final List<String> UN_AUTHENTICATION_PATH = List.of(LOGIN_PATH, REFRESH_TOKEN_PATH);

    // Check-in
    public static final int EARTH_RADIUS = 6371; // Earth Radius in Kilometers
    public static final double VALID_DISTANCE_CHECK_IN = 0.1;  // Valid distance is 100 meters

}
