package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.EnrollmentEntity;
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
            "and e.user.userId in :userIds")
    List<EnrollmentEntity> findAllByClassIdAndUserIds(@Param("classId") UUID classId, @Param("userIds") Collection<UUID> userIds);

    @Query("select e from EnrollmentEntity e " +
            "where e.clazz.classId = :classId " +
            "and e.status = 1")
    List<EnrollmentEntity> findAllWithStatusApproved(@Param("classId") UUID classId);
}
