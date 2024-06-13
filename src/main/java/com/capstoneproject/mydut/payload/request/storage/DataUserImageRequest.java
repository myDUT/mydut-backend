package com.capstoneproject.mydut.payload.request.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vndat00
 * @since 6/11/2024
 */

@Getter
@Setter
@NoArgsConstructor
public class DataUserImageRequest {
    private String path;
    private String searchText;
    private boolean isPublicBucket;
    private boolean isRecursive;
}
