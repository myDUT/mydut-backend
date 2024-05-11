package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, UUID> {
}
