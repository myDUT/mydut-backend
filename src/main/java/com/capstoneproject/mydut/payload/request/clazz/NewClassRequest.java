package com.capstoneproject.mydut.payload.request.clazz;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@Getter
@Setter
@NoArgsConstructor
public class NewClassRequest {
    private String roomId;
    private String name;
    private String classCode;
    private Integer dayOfWeek;
    private String timeFrom;
    private String timeTo;
    private String dateFrom;
    private String dateTo;
}
