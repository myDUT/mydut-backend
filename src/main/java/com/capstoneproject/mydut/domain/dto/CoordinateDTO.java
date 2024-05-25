package com.capstoneproject.mydut.domain.dto;

import lombok.*;

/**
 * @author vndat00
 * @since 5/26/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class CoordinateDTO {
    private String longitude;
    private String latitude;
}
