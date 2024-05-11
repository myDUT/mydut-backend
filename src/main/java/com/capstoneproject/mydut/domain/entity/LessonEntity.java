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
@Table(name = "lesson")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private UUID lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", referencedColumnName = "class_id", nullable = false)
    private ClassEntity clazz;

    @OneToOne
    @JoinColumn(name = "coordinate_id")
    private CoordinateEntity coordinate;

    @Column(name = "datetime_from", nullable = false)
    private Timestamp datetimeFrom;

    @Column(name = "datetime_to", nullable = false)
    private Timestamp datetimeTo;

    @Column(name = "description")
    private String description;

    @Column(name = "present_student")
    private Integer presentStudent;

    @Column(name = "absent_student")
    private Integer absentStudent;
}
