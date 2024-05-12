package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, UUID> {
    @Query("select c from ClassEntity  c where c.createdBy = :userId")
    List<ClassEntity> findAllCreatedClasses(@Param("userId") UUID userId);

    @Query("select c, e from EnrollmentEntity e " +
            "join fetch e.user u " +
            "join fetch e.clazz c " +
            "where u.userId = :userId and e.status = 'approved'")
    List<Tuple> findAllClassesBelongTo(@Param("userId") UUID userId);
}
