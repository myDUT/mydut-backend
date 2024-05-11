package com.capstoneproject.mydut.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author vndat00
 * @since 5/8/2024
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "coordinate")
public class CoordinateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coordinate_id")
    private UUID coordinateId;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @OneToOne(mappedBy = "coordinate")
    private LessonEntity lesson;

    @OneToOne(mappedBy = "coordinate")
    private AttendanceRecordEntity attendanceRecord;
}
