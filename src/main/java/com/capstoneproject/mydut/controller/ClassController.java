package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.payload.request.clazz.NewClassRequest;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassController {

    private final ClassService classService;

    @PostMapping
    @Secured(value = {
            MyDUTPermission.Role.ADMIN,
            MyDUTPermission.Role.TEACHER
    })
    public Response<OnlyIdDTO> createClass(@RequestBody NewClassRequest request) throws ParseException {
        return classService.createClass(request);
    }
}
