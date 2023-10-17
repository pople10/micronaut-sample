/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories.entities.audit;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.sample.utils.enumerations.EntityStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@MappedSuperclass
@DynamicInsert
public class TimeStampEntity implements Serializable {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date modifyDate;

    @Column(length = 32, columnDefinition = "varchar(32) default 'CREATED'", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
}
