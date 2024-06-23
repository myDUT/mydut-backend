package com.capstoneproject.mydut.domain.repository;

import com.capstoneproject.mydut.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/10/2024
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("select u from UserEntity u " +
            "left join fetch u.role " +
            "where u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query("select u from UserEntity u " +
            "where u.studentCode in :studentCodes")
    List<UserEntity> findAllByStudentCodes(@Param("studentCodes") Collection<String> studentCodes);

}
