package com.capstoneproject.mydut.service.impl;

import com.capstoneproject.mydut.converter.ClassConverter;
import com.capstoneproject.mydut.domain.entity.ClassEntity;
import com.capstoneproject.mydut.domain.entity.LessonEntity;
import com.capstoneproject.mydut.domain.repository.*;
import com.capstoneproject.mydut.exception.ObjectNotFoundException;
import com.capstoneproject.mydut.payload.request.clazz.NewClassRequest;
import com.capstoneproject.mydut.payload.request.clazz.UpdateClassRequest;
import com.capstoneproject.mydut.payload.response.ClassDTO;
import com.capstoneproject.mydut.payload.response.NoContentDTO;
import com.capstoneproject.mydut.payload.response.OnlyIdDTO;
import com.capstoneproject.mydut.payload.response.Response;
import com.capstoneproject.mydut.service.ClassService;
import com.capstoneproject.mydut.util.DateTimeUtils;
import com.capstoneproject.mydut.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author vndat00
 * @since 5/12/2024
 */

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final SecurityUtils securityUtils;
    private final ClassRepository classRepository;
    private final RoomRepository roomRepository;
    private final LessonRepository lessonRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EvidenceImageRepository evidenceImageRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final CoordinateRepository coordinateRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Response<OnlyIdDTO> createClass(NewClassRequest request) {
        // TODO: Validate newClass request

        var principal = securityUtils.getPrincipal();

        ClassEntity classEntity = new ClassEntity();

        var room = roomRepository.findById(UUID.fromString(request.getRoomId())).orElseThrow(() ->
                new ObjectNotFoundException("roomId", request.getRoomId()));
        classEntity.setRoom(room);

        classEntity.setName(StringUtils.defaultString(request.getName()));
        classEntity.setDayOfWeek(request.getDayOfWeek() != null ? request.getDayOfWeek() : 0);
        classEntity.setClassCode(StringUtils.defaultString(request.getClassCode()));
        classEntity.setTotalStudent(0);
        classEntity.setIsDeleted(Boolean.FALSE);

        Date dateFrom = DateTimeUtils.move2BeginTimeOfDay(DateTimeUtils.string2Timestamp(request.getDateFrom()));
        Date dateTo = DateTimeUtils.move2EndTimeOfDay(DateTimeUtils.string2Timestamp(request.getDateTo()));
        Date timeFrom = DateTimeUtils.resetSecondOfDateTime(DateTimeUtils.string2Timestamp(request.getTimeFrom()));
        Date timeTo = DateTimeUtils.resetSecondOfDateTime(DateTimeUtils.string2Timestamp(request.getTimeTo()));

        classEntity.setDateFrom(new Timestamp(dateFrom.getTime()));
        classEntity.setDateTo(new Timestamp(dateTo.getTime()));
        classEntity.setTimeFrom(new Timestamp(timeFrom.getTime()));
        classEntity.setTimeTo(new Timestamp(timeTo.getTime()));

        classEntity.setLecturer(principal.getFullName());

        classEntity.prePersist(UUID.fromString(principal.getUserId()));

        var savedClass = classRepository.save(classEntity);

        List<Date> dates = createDateInRange(dateFrom, dateTo, request.getDayOfWeek());

        List<LessonEntity> lessons = new ArrayList<>();

        for (Date date : dates) {
            LessonEntity lesson = new LessonEntity();

            lesson.setClazz(savedClass);

            Date datetimeFrom = DateTimeUtils.generateDateTimeFromDateAndTime(date, timeFrom);
            Date datetimeTo = DateTimeUtils.generateDateTimeFromDateAndTime(date, timeTo);

            lesson.setDatetimeFrom(new Timestamp(datetimeFrom.getTime()));
            lesson.setDatetimeTo(new Timestamp(datetimeTo.getTime()));

            lesson.setIsEnableCheckIn(Boolean.FALSE);

            lessons.add(lesson);
        }

        lessonRepository.saveAll(lessons);

        return Response.<OnlyIdDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Create class successfully.")
                .setData(OnlyIdDTO.newBuilder()
                        .setId(String.valueOf(savedClass.getClassId()))
                        .build())
                .build();
    }

    public static List<Date> createDateInRange(Date start, Date end, Integer dayOfWeek) {
        List<Date> result = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(start);

        while (!cal.getTime().after(end)) {
            if (cal.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                result.add(cal.getTime());
            }
            cal.add(Calendar.DATE, 1);
        }
        return result;
    }

    @Override
    @Transactional
    public Response<OnlyIdDTO> updateClass(String classId, UpdateClassRequest request) {
        // TODO: validate updateClass request
        var clazz = classRepository.findById(UUID.fromString(classId)).orElseThrow(() ->
                new ObjectNotFoundException("classId", classId));
        if (!clazz.getRoom().getRoomId().toString().equalsIgnoreCase(request.getRoomId())) {
            var newRoom = roomRepository.findById(UUID.fromString(request.getRoomId())).orElseThrow(() ->
                    new ObjectNotFoundException("roomId", request.getRoomId()));
            clazz.setRoom(newRoom);
        }
        clazz.setName(StringUtils.defaultString(request.getName()));
        clazz.setClassCode(StringUtils.defaultString(request.getClassCode()));

        classRepository.save(clazz);

        return Response.<OnlyIdDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Update class information successfully.")
                .setData(OnlyIdDTO.newBuilder()
                        .setId(classId)
                        .build())
                .build();
    }

    @Override
    public Response<NoContentDTO> deleteClass(String classId) {
        var clazz = classRepository.findById(UUID.fromString(classId)).orElseThrow(() ->
                new ObjectNotFoundException("classId", classId));

        clazz.setIsDeleted(Boolean.TRUE);

        classRepository.save(clazz);

        return Response.<NoContentDTO>newBuilder()
                .setSuccess(true)
                .setMessage("Deleted class successfully.")
                .build();
    }

    @Override
    public Response<List<ClassDTO>> getAllClass() {
        return null;
    }

    @Override
    public Response<List<ClassDTO>> getAllClassesDependOnPrincipal() {
        var principal = securityUtils.getPrincipal();

        List<ClassEntity> classes;

        if (Boolean.TRUE.equals(principal.isTeacher())) {
            classes = classRepository.findAllCreatedClasses(UUID.fromString(principal.getUserId()));


        } else if (Boolean.TRUE.equals(principal.isStudent())) {

            classes = classRepository.findAllClassesBelongTo(UUID.fromString(principal.getUserId()));

        } else {
            classes = classRepository.findAllNoDelete();
        }
        if (!CollectionUtils.isEmpty(classes)) {
            return Response.<List<ClassDTO>>newBuilder()
                    .setSuccess(true)
                    .setMessage("All classes you have created or belong to have been successfully retrieved.")
                    .setData(classes.stream()
                            .map(c -> {
                                ClassDTO.ClassDTOBuilder builder = ClassConverter.entityToBuilder(c);

                                if (!Boolean.TRUE.equals(principal.isTeacher()) ) {
                                    var lecturer = userRepository.findById(c.getCreatedBy()).orElseThrow(() ->
                                            new ObjectNotFoundException("userId", c.getCreatedBy()));
                                    builder.setLecturer(lecturer.getFullName());
                                }
                                return builder.build();
                            })
                            .collect(Collectors.toList()))
                    .build();
        }

        return Response.<List<ClassDTO>>newBuilder()
                .setSuccess(true)
                .setMessage("No classes created/classes owned by you.")
                .build();
    }
}


