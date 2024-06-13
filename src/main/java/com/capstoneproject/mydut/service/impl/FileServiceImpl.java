package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.helper.storage.MinioService;
import com.capstoneproject.mydut.payload.request.storage.DataUserImageRequest;
import com.capstoneproject.mydut.payload.request.storage.UploadDataUserImagesRequest;
import com.capstoneproject.mydut.payload.response.FileItemDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.FileService;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.capstoneproject.mydut.common.constants.Constant.PREFIX_UPLOAD_DATA_IMAGE;

/**
 * @author vndat00
 * @since 6/11/2024
 */

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final MinioService minioService;
    private final SecurityUtils securityUtils;

    @Override
    public Response<List<FileItemDTO>> listFile(DataUserImageRequest request) {
        var principal = securityUtils.getPrincipal();
        var prefix = String.format("%s%s", PREFIX_UPLOAD_DATA_IMAGE, principal.getStudentCode());

        var files = minioService.listFile(prefix, request.getSearchText(), request.isPublicBucket(), request.isRecursive());
        return Response.<List<FileItemDTO>>newBuilder()
                .setSuccess(true)
                .setMessage("Get all file successfully.")
                .setData(files)
                .build();
    }

    @Override
    public Response<List<FileItemDTO>> uploadImages(UploadDataUserImagesRequest request) {
        var principal = securityUtils.getPrincipal();
        var prefix = String.format("%s%s", PREFIX_UPLOAD_DATA_IMAGE, principal.getStudentCode());

        var preSignedUrls = minioService.uploadFile(request.getFileNames(), prefix, false, true);


        return Response.<List<FileItemDTO>>newBuilder()
                .setSuccess(true)
                .setMessage("Get PreSignedUrl to upload.")
                .setData(preSignedUrls)
                .build();
    }
}
