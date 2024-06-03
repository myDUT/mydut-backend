package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.AttendanceRecordEntity;
import com.capstoneproject.mydut.domain.projection.AttendanceRecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecordEntity, UUID> {

    @Query("select ar from AttendanceRecordEntity  ar where ar.user.userId = :userId and ar.lesson.lessonId = :lessonId")
    Optional<AttendanceRecordEntity> findByUserIdAndLessonId(@Param("userId") UUID userId, @Param("lessonId") UUID lessonId);

    @Query("select count(distinct ar) from AttendanceRecordEntity ar join ar.lesson l " +
            "where l.lessonId = :lessonId " +
            "and (ar.isValidCheckIn = true or ar.isFacialRecognition = true)")
    Integer countValidCheckInByLessonId(@Param("lessonId") UUID lessonId);

    @Query("select ar.attendanceRecordId as attendanceRecordId, u.userId as userId, l.lessonId as lessonId, " +
            "ar.timeIn as timeIn, ar.isFacialRecognition as isFacialRecognition, ar.isValidCheckIn as isValidCheckIn, ar.distance as distance " +
            "from AttendanceRecordEntity  ar " +
            "join ar.lesson l " +
            "join ar.user u " +
            "where l.lessonId = :lessonId")
    List<AttendanceRecordDetail> getAttendanceReportByLessonId(@Param("lessonId") UUID lessonId);
}
