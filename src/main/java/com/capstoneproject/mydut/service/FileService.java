package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.storage.DataUserImageRequest;
import com.capstoneproject.mydut.payload.request.storage.UploadDataUserImagesRequest;
import com.capstoneproject.mydut.payload.response.FileItemDTO;
import com.capstoneproject.mydut.payload.response.Response;

import java.util.List;

/**
 * @author vndat00
 * @since 6/11/2024
 */
public interface FileService {
    Response<List<FileItemDTO>> listFile(DataUserImageRequest request);
    Response<List<FileItemDTO>> uploadImages(UploadDataUserImagesRequest request);
}
