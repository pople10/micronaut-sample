/* Mohammed Amine AYACHE (C)2022 */
package com.sample.services;

import com.sample.models.responses.NotificationResponse;
import com.sample.utils.enumerations.NotificationType;
import io.micronaut.scheduling.annotation.Async;
import java.util.List;

import com.sample.repositories.entities.User;

public interface NotificationService {
    @Async
    void addNotification(NotificationType type, User user, String redirectTo);

    List<NotificationResponse> findUnreadNotification(User user);

    void makeNotificationsRead(User user);
}
