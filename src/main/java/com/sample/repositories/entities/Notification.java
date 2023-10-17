/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories.entities;

import javax.persistence.*;

import com.sample.utils.enumerations.NotificationType;
import lombok.Getter;
import lombok.Setter;
import com.sample.repositories.entities.audit.TimeStampEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@SQLDelete(
        sql = "UPDATE notification SET status = 'DELETED' WHERE id = ?",
        check = ResultCheckStyle.COUNT)
@Where(clause = "status <> 'DELETED'")
@DynamicInsert
public class Notification extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne private User user;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private String redirectTo;

    @Column(name = "is_read")
    private boolean read;
}
