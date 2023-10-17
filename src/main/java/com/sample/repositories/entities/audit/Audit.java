/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories.entities.audit;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.sample.utils.enumerations.AuditActions;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Setter
@Getter
@Entity
@ToString
public class Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String tableName;

    private String actor;

    @Enumerated(EnumType.STRING)
    private AuditActions action;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createDate;
}
