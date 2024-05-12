package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.clazz.NewClassRequest;
import com.capstoneproject.mydut.payload.request.clazz.UpdateClassRequest;
import com.capstoneproject.mydut.payload.response.ClassDTO;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;

import java.text.ParseException;
import java.util.List;

/**
 * @author vndat00
 * @since 5/10/2024
 */

public interface ClassService {
    public Response<OnlyIdDTO> createClass(NewClassRequest request) throws ParseException;
    public Response<OnlyIdDTO> updateClass(UpdateClassRequest request);
    public Response<NoContentDTO> deleteClass(OnlyIdDTO request);
    public Response<List<ClassDTO>> getAllClass();
    public Response<List<ClassDTO>> getAllClassesDependOnPrincipal();
    public Response<ClassDTO> getClassAndAllLessonsById(OnlyIdDTO request);

}
