package com.capstoneproject.mydut.payload.response;

import lombok.*;

import java.io.Serializable;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Getter
@Setter
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class OnlyIdDTO implements Serializable {
    private String id;
}
