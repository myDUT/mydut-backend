package com.capstoneproject.mydut.helper.storage;

import com.capstoneproject.mydut.common.enums.PreSignedUrlType;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author vndat00
 * @since 6/9/2024
 */
public interface StorageInterface {
    String getPreSignedUrl(String key, int expires, int type, boolean isPublicBucket)  throws Exception;
    void deleteFile(String key, boolean isPublicBucket) throws Exception;

}
