package com.capstoneproject.mydut.payload.request.lesson;

import com.capstoneproject.mydut.domain.dto.CoordinateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vndat00
 * @since 5/26/2024
 */

@Getter
@Setter
@NoArgsConstructor
public class StartCheckInRequest {
    private String lessonId;
    private CoordinateDTO coordinateDTO;
}
