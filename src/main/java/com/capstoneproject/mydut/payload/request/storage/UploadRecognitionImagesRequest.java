package com.capstoneproject.mydut.payload.request.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author vndat00
 * @since 6/22/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadRecognitionImagesRequest {
    private List<String> fileNames;
    private String lessonId;
    private String classCode;
    private String classId;
}
