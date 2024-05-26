package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.ClassEntity;
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
public interface ClassRepository extends JpaRepository<ClassEntity, UUID> {
    @Query("select c from ClassEntity  c where c.createdBy = :userId and c.isDeleted = false")
    List<ClassEntity> findAllCreatedClasses(@Param("userId") UUID userId);

    @Query("select c from ClassEntity c " +
            "join EnrollmentEntity e on c.classId = e.clazz.classId " +
            "join UserEntity u on e.user.userId = u.userId " +
            "where u.userId = :userId and e.status = 1 and c.isDeleted = false")
    List<ClassEntity> findAllClassesBelongTo(@Param("userId") UUID userId);

    @Query("select c from ClassEntity c " +
            "where c.isDeleted = false")
    List<ClassEntity> findAllNoDelete();

    @Query("select c from ClassEntity  c where c.classCode = :classCode")
    Optional<ClassEntity> findByClassCode(@Param("classCode") String classCode);
}
