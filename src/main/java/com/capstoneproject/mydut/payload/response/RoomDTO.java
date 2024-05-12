package com.capstoneproject.mydut.payload.response;

import lombok.*;

/**
 * @author vndat00
 * @since 5/11/2024
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "set", builderMethodName = "newBuilder")
public class RoomDTO {
    private String roomId;
    private String name;
}
