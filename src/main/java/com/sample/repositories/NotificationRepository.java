/* Mohammed Amine AYACHE (C)2022 */
package com.sample.repositories;

import com.sample.models.responses.NotificationResponse;
import io.micronaut.data.annotation.Repository;
import java.util.List;
import javax.persistence.Cacheable;

import com.sample.repositories.entities.Notification;
import com.sample.repositories.entities.User;

@Repository
@Cacheable()
public interface NotificationRepository extends GenericRepository<Notification, Long> {
    List<NotificationResponse> findByReadAndUserOrderByCreateDateDesc(boolean isRead, User user);
}
