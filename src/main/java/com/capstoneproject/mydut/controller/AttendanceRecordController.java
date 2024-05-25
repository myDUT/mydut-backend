package com.capstoneproject.mydut.controller;

import com.capstoneproject.mydut.common.constants.MyDUTPermission;
import com.capstoneproject.mydut.payload.request.attendancerecord.AttendanceRecordRequest;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.AttendanceRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author vndat00
 * @since 5/24/2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/attendance-records")
public class AttendanceRecordController {
    private final AttendanceRecordService attendanceRecordService;

    @PostMapping
    @Secured(value = {
            MyDUTPermission.Role.STUDENT
    })
    public Response<OnlyIdDTO> checkIn(@RequestBody AttendanceRecordRequest request) {
        return attendanceRecordService.checkIn(request);
    }


}
