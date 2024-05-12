package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.payload.request.clazz.NewClassRequest;
import com.capstoneproject.mydut.payload.request.clazz.UpdateClassRequest;
import com.capstoneproject.mydut.payload.response.ClassDTO;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

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

    @PutMapping("{id}")
    @Secured(value = {
            MyDUTPermission.Role.ADMIN,
            MyDUTPermission.Role.TEACHER
    })
    public Response<OnlyIdDTO> updateClass(@PathVariable("id") String classId, @RequestBody UpdateClassRequest request) {
        return classService.updateClass(classId, request);
    }

    @DeleteMapping("{id}")
    @Secured(value = {
            MyDUTPermission.Role.ADMIN,
            MyDUTPermission.Role.TEACHER
    })
    public Response<NoContentDTO> deleteClass(@PathVariable("id") String classId) {
        return classService.deleteClass(classId);
    }

    @GetMapping
    @Secured(value = {
            MyDUTPermission.Role.TEACHER,
            MyDUTPermission.Role.STUDENT
    })
    public Response<List<ClassDTO>> getAllClassesBelongTo() {
        return classService.getAllClassesDependOnPrincipal();
    }
}
