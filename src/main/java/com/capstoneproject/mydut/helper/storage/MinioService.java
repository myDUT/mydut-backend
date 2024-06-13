package com.capstoneproject.mydut.helper.storage;

import com.capstoneproject.mydut.common.enums.PreSignedUrlType;
import com.capstoneproject.mydut.payload.response.FileItemDTO;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @author vndat00
 * @since 6/10/2024
 */

@Component
@Slf4j
public class MinioService implements StorageInterface {
    private final int defaultExpireTime = 12 * 60 * 60;
    private final Environment environment;
    private final MinioClient minioClient;

    public MinioService(Environment environment) {
        String minioUrl = environment.getProperty("minio.url");
        String accessKey = environment.getProperty("app.minio.access-key");
        String secretKey = environment.getProperty("app.minio.secret-key");

        if (StringUtils.isEmpty(minioUrl) || StringUtils.isEmpty(accessKey) || StringUtils.isEmpty(secretKey)) {
            throw new IllegalArgumentException("MinIO URL, Access Key, and Secret Key must not be null or empty");
        }

        this.environment = environment;
        this.minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    public List<FileItemDTO> listFile(String key, String searchValue, boolean isPublicBucket, boolean isRecursive) {
        // Ensure the directoryPath ends with "/"
        if (!key.endsWith("/")) {
            key += "/";
        }
        ListObjectsArgs.Builder listObjectsArgs = ListObjectsArgs.builder()
                .bucket(isPublicBucket ? environment.getRequiredProperty("app.minio.bucket.public") : environment.getRequiredProperty("app.minio.bucket.private"))
                .recursive(isRecursive).includeUserMetadata(true)
                .prefix(key);
        if (StringUtils.isBlank(searchValue)) {
            listObjectsArgs.delimiter("/");
        } else {
            listObjectsArgs.recursive(true);
        }
        var items = minioClient.listObjects(listObjectsArgs.build());
        var fileItems = new ArrayList<FileItemDTO>();
        items.forEach(item -> {
            try {
                String objectKey = item.get().objectName();
                String fileName = objectKey.substring(objectKey.lastIndexOf("/") + 1);
                if (fileName.contains(searchValue)) {
                    fileItems.add(FileItemDTO.newBuilder()
                            .setKey(objectKey)
                            .setContentType(item.get().userMetadata() != null ? item.get().userMetadata().get("content-type") : "")
                            .setUrl(item.get().userMetadata() != null ? this.getPreSignedUrl(objectKey, defaultExpireTime, PreSignedUrlType.PRE_SIGNED_URL_TYPE_DOWNLOAD.getId(), isPublicBucket) : "")
                            .build());
                }
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
        return fileItems;
    }

    public List<FileItemDTO> uploadFile(List<String> fileNames, String prefix, boolean isPublicBucket, boolean needClearBeforeUpload) {
        List<FileItemDTO> fileItems = new ArrayList<>();

        if (!prefix.endsWith("/")) {
            prefix += "/";
        }

        String finalPrefix = prefix;

        // Clear data before upload
        if (needClearBeforeUpload) {
            String bucketName = isPublicBucket
                    ? environment.getRequiredProperty("app.minio.bucket.public")
                    : environment.getRequiredProperty("app.minio.bucket.private");

            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(prefix)
                            .recursive(true)
                            .build());

            results.forEach(result -> {
                try {
                    deleteFile(result.get().objectName(), isPublicBucket);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        List<String> objectNames = fileNames.stream()
                .map(n -> String.format("%s%s", finalPrefix, n))
                .collect(Collectors.toList());

        objectNames.forEach(objectName -> {
            try {
                fileItems.add(FileItemDTO.newBuilder()
                        .setKey(objectName)
                        .setContentType("")
                        .setUrl(this.getPreSignedUrl(objectName, defaultExpireTime, PreSignedUrlType.PRE_SIGNED_URL_TYPE_UPLOAD.getId(), isPublicBucket))
                        .build());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return fileItems;
    }

    @Override
    public String getPreSignedUrl(String key, int expires, int type, boolean isPublicBucket) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(PreSignedUrlType.PRE_SIGNED_URL_TYPE_DOWNLOAD.getId().equals(type) ? Method.GET : Method.PUT)
                        .bucket(isPublicBucket ? environment.getRequiredProperty("app.minio.bucket.public") : environment.getRequiredProperty("app.minio.bucket.private"))
                        .object(key)
                        .expiry(expires > 0 ? expires : defaultExpireTime)
                        .build());
    }

    @Override
    public void deleteFile(String key, boolean isPublicBucket) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(isPublicBucket ? environment.getRequiredProperty("app.minio.bucket.public") : environment.getRequiredProperty("app.minio.bucket.private"))
                        .object(key)
                        .build()
        );
    }
}
