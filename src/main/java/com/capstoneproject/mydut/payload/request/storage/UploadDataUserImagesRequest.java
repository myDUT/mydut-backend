package com.capstoneproject.mydut.payload.request.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author vndat00
 * @since 6/12/2024
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadDataUserImagesRequest {
    private List<String> fileNames;
}
