package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.EnrollmentEntity;
import com.capstoneproject.mydut.domain.projection.EnrolledStudent;
import com.capstoneproject.mydut.payload.response.EnrolledStudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {

    @Query("select e from EnrollmentEntity e " +
            "where e.clazz.classId = :classId " +
            "and e.user.userId in :userIds " +
            "and e.status != 0")
    List<EnrollmentEntity> findAllByClassIdAndUserIds(@Param("classId") UUID classId, @Param("userIds") Collection<UUID> userIds);

    @Query("select e from EnrollmentEntity e " +
            "where e.clazz.classId = :classId " +
            "and e.status = 1")
    List<EnrollmentEntity> findAllWithStatusApproved(@Param("classId") UUID classId);


    @Query("select e.enrollmentId as enrollmentId, u.userId as userId, u.username as username, u.fullName as fullName, u.studentCode as studentCode," +
            " u.homeroomClass as homeroomClass, u.email as email, c.classId as classId, c.name as className, e.status as status " +
            "from EnrollmentEntity  e " +
            "join e.user u " +
            "join e.clazz c " +
            "where c.classId = :classId " +
            "and e.status != 0 " +
            "order by status desc")
    List<EnrolledStudent> findAllEnrolledStudentByClassId(@Param("classId") UUID classId);

    @Query("select e.enrollmentId as enrollmentId, u.userId as userId, u.username as username, u.fullName as fullName, u.studentCode as studentCode," +
            " u.homeroomClass as homeroomClass, u.email as email, c.classId as classId, c.name as className, e.status as status " +
            "from EnrollmentEntity  e " +
            "join e.user u " +
            "join e.clazz c " +
            "where c.classId = :classId " +
            "and e.status = 1 ")
    List<EnrolledStudent> findAllApprovedStudentByClassId(@Param("classId") UUID classId);

    @Query("select count(e) " +
            "from EnrollmentEntity e " +
            "join e.clazz c " +
            "where e.status = 2 " +
            "and c.classId = :classId")
    Integer countWaitingEnrollmentInClass(@Param("classId") UUID classId);
}
