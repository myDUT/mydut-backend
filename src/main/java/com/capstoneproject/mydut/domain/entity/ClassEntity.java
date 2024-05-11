package com.capstoneproject.mydut.domain.entity;

import lombok.*;

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
@Table(name = "clazz")
public class ClassEntity extends Auditable<UUID> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "class_id")
    private UUID classId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private RoomEntity room;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "day_of_week", nullable = false)
    private Integer dayOfWeek;

    @Column(name = "time_from", nullable = false)
    private Timestamp timeFrom;

    @Column(name = "time_to", nullable = false)
    private Timestamp timeTo;

    @Column(name = "date_from", nullable = false)
    private Timestamp dateFrom;

    @Column(name = "date_to", nullable = false)
    private Timestamp dateTo;

    @Column(name = "total_student")
    private Integer totalStudent;

    @Column(name = "class_code", nullable = false)
    private String classCode;
}
