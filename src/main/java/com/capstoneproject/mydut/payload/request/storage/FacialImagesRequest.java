package com.capstoneproject.mydut.payload.request.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vndat00
 * @since 6/23/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacialImagesRequest {
    private String classId;
    private String classCode;
    private String lessonId;
    private boolean isPublicBucket;
    private boolean isRecursive;
}
