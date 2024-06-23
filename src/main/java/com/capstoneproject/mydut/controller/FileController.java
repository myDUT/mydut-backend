package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.payload.request.storage.DataUserImageRequest;
import com.capstoneproject.mydut.payload.request.storage.FacialImagesRequest;
import com.capstoneproject.mydut.payload.request.storage.UploadDataUserImagesRequest;
import com.capstoneproject.mydut.payload.request.storage.UploadRecognitionImagesRequest;
import com.capstoneproject.mydut.payload.response.FileItemDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author vndat00
 * @since 6/9/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/storages")
public class FileController {
    private final FileService fileService;

    @PostMapping("/data-image-user")
    @Secured(value = {
            MyDUTPermission.Role.STUDENT
    })
    public Response<List<FileItemDTO>> getAllDataImagesUser(@RequestBody DataUserImageRequest request) {
        return fileService.listFile(request);
    }

    @PostMapping("/facial-image-data")
    @Secured(value = {
            MyDUTPermission.Role.TEACHER,
            MyDUTPermission.Role.STUDENT
    })
    public Response<List<FileItemDTO>> getAllRecognizedImageEachLesson(@RequestBody FacialImagesRequest request) {
        return fileService.listFacialImagesInEachLesson(request);
    }

    @PostMapping("/upload-files")
    @Secured(value = {
            MyDUTPermission.Role.STUDENT
    })
    public Response<List<FileItemDTO>> uploadUserDataImages(@RequestBody UploadDataUserImagesRequest request) {
        return fileService.uploadImages(request);
    }

    @PostMapping("/upload-recognition-images")
    @Secured(value = {
            MyDUTPermission.Role.TEACHER
    })
    public Response<List<FileItemDTO>> uploadRecognitionImages(@RequestBody UploadRecognitionImagesRequest request) {
        return fileService.uploadRecognitionImages(request);
    }

}
