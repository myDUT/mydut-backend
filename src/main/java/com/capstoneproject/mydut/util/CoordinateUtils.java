package com.capstoneproject.mydut.util;

import com.capstoneproject.mydut.domain.dto.CoordinateDTO;

import static com.capstoneproject.mydut.common.constants.Constant.EARTH_RADIUS;

/**
 * @author vndat00
 * @since 5/26/2024
 */
public class CoordinateUtils {
    public static Double calcDistance(CoordinateDTO studentCoordinate, CoordinateDTO landmarkCoordinate) {
        double latStudent = Double.parseDouble(studentCoordinate.getLatitude());
        double lonStudent = Double.parseDouble(studentCoordinate.getLongitude());
        double latLandmark = Double.parseDouble(landmarkCoordinate.getLatitude());
        double lonLandmark = Double.parseDouble(landmarkCoordinate.getLongitude());

        // Convert latitude and longitude from degrees to radians
        double latStudentRad = Math.toRadians(latStudent);
        double lonStudentRad = Math.toRadians(lonStudent);
        double latLandMarkRad = Math.toRadians(latLandmark);
        double lonLandmarkRad = Math.toRadians(lonLandmark);

        // Haversine formula
        double dLat = latLandMarkRad - latStudentRad;
        double dLon = lonLandmarkRad - lonStudentRad;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(latStudentRad) * Math.cos(latLandMarkRad)
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance in kilometers
        return EARTH_RADIUS * c;
    }

    public static void main(String[] args) {
        CoordinateDTO st = CoordinateDTO.newBuilder()
                .setLongitude("108.24187345014248")
                .setLatitude("16.04342389095366")
                .build();
        CoordinateDTO lm = CoordinateDTO.newBuilder()
                .setLongitude("108.24200778579674")
                .setLatitude("16.043229813506287")
                .build();

        System.out.println(calcDistance(st, lm) * 1000);
    }
}
