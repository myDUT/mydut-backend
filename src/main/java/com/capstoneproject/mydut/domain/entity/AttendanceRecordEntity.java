package com.capstoneproject.mydut.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/9/2024
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "attendance_record")
public class AttendanceRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attendance_record_id")
    private UUID attendanceRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", referencedColumnName = "lesson_id", nullable = false)
    private LessonEntity lesson;

    @OneToOne
    @JoinColumn(name = "coordinate_id", nullable = false)
    private CoordinateEntity coordinate;

    @Column(name = "time_in", nullable = false)
    private Timestamp timeIn;

    @Column(name = "is_valid_check_in", nullable = false)
    private Boolean isValidCheckIn;

    @Column(name = "is_facial_recognition", nullable = false)
    private Boolean isFacialRecognition;

    @Column(name = "distance")
    private Double distance;

}
