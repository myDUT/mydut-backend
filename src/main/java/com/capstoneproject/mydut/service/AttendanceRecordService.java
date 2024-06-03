package com.capstoneproject.mydut.service;

import com.capstoneproject.mydut.payload.request.attendancerecord.AttendanceRecordRequest;
import com.capstoneproject.mydut.payload.request.attendancerecord.AttendanceReportRequest;
import com.capstoneproject.mydut.payload.response.AttendanceRecordDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;

import java.util.List;

/**
 * @author vndat00
 * @since 5/10/2024
 */
public interface AttendanceRecordService {
    Response<OnlyIdDTO> checkIn(AttendanceRecordRequest request);
    Response<List<AttendanceRecordDTO>> getAttendanceReportOfLesson(AttendanceReportRequest request);
}
