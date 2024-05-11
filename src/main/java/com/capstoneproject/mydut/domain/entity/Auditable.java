package com.capstoneproject.mydut.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author vndat00
 * @since 5/8/2024
 */

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(setterPrefix = "set", builderMethodName = "newBuilder")
public abstract class Auditable<U extends Serializable> implements Serializable {
    @Column(name = "created_by")
    private U createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private U updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public void prePersist(U userId) {
        var currentTimeMillis = System.currentTimeMillis();
        var now = new Timestamp(currentTimeMillis);
        this.createdAt = now;
        this.createdBy = userId;
        this.updatedAt = now;
        this.updatedBy = userId;
    }

    public void preUpdate(U userId) {
        var currentTimeMillis = System.currentTimeMillis();
        this.updatedAt = new Timestamp(currentTimeMillis);
        this.updatedBy = userId;
    }
}
