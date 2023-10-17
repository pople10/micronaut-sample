/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils;

import io.micronaut.scheduling.annotation.Async;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import com.sample.repositories.entities.User;
import com.sample.services.NotificationService;
import com.sample.utils.enumerations.NotificationType;

@Singleton
@RequiredArgsConstructor
public class NotificationAsyncService {
    private final NotificationService notificationService;

    @Async
    public void addNotification(NotificationType type, User user, String redirectTo) {
        notificationService.addNotification(type, user, redirectTo);
    }

    @Async
    public void addNotificationMessage(User user, String redirectTo) {
        addNotification(NotificationType.MESSAGE_RECEIVED, user, redirectTo);
    }

    @Async
    public void addNotificationRequestReceived(User user, String redirectTo) {
        addNotification(NotificationType.REQUEST_ADDED, user, redirectTo);
    }

    @Async
    public void addNotificationRequestApproved(User user) {
        addNotification(NotificationType.REQUEST_APPROVED, user, null);
    }
}
