package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.LessonEntity;
import com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
    @Query("select new com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO(" +
            "c.classId, l.lessonId, c.name, c.classCode, c.room.roomId, c.room.name, l.datetimeFrom, l.datetimeTo, c.lecturer, c.totalStudent, l.presentStudent, l.isEnableCheckIn" +
            ") from LessonEntity l " +
            "inner join ClassEntity c on l.clazz.classId = c.classId " +
            "inner join EnrollmentEntity e on c.classId = e.clazz.classId " +
            "inner join UserEntity  u on e.user.userId = u.userId " +
            "where u.userId = :userId " +
            "and e.status = 1 " +
            "and l.datetimeFrom >= :dateTimeFrom " +
            "and l.datetimeTo <= :dateTimeTo " +
            "order by l.datetimeFrom asc")
    List<GeneralInfoLessonDTO> findAllLessonsBelongToStudentInOneDay(
            @Param("userId") UUID userId,
            @Param("dateTimeFrom") Timestamp dateTimeFrom,
            @Param("dateTimeTo") Timestamp dateTimeTo);

    @Query("select new com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO(" +
            "c.classId, l.lessonId, c.name, c.classCode, c.room.roomId, c.room.name, l.datetimeFrom, l.datetimeTo, c.lecturer, c.totalStudent, l.presentStudent, l.isEnableCheckIn" +
            ") from LessonEntity l " +
            "inner join ClassEntity c on l.clazz.classId = c.classId " +
            "where c.createdBy = :userId " +
            "and l.datetimeFrom >= :dateTimeFrom " +
            "and l.datetimeTo <= :dateTimeTo " +
            "order by l.datetimeFrom asc")
    List<GeneralInfoLessonDTO> findAllLessonsCreatedByTeacherInOneDay(
            @Param("userId") UUID userId,
            @Param("dateTimeFrom") Timestamp dateTimeFrom,
            @Param("dateTimeTo") Timestamp dateTimeTo);

    @Query("select new com.capstoneproject.mydut.payload.response.GeneralInfoLessonDTO(" +
            "c.classId, l.lessonId, c.name, c.classCode, c.room.roomId, c.room.name, l.datetimeFrom, l.datetimeTo, c.lecturer, c.totalStudent, l.presentStudent, l.isEnableCheckIn" +
            ") from LessonEntity l " +
            "inner join ClassEntity c on l.clazz.classId = c.classId " +
            "where l.datetimeFrom >= :dateTimeFrom " +
            "and l.datetimeTo <= :dateTimeTo " +
            "order by l.datetimeFrom asc")
    List<GeneralInfoLessonDTO> findAllLessonsInOneDay(
            @Param("dateTimeFrom") Timestamp dateTimeFrom,
            @Param("dateTimeTo") Timestamp dateTimeTo);
}
