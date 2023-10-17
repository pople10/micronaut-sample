/* Mohammed Amine AYACHE (C)2022 */
package com.sample.services.Imp;

import com.sample.models.responses.NotificationResponse;
import com.sample.services.NotificationService;
import com.sample.utils.enumerations.NotificationType;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.sample.repositories.NotificationRepository;
import com.sample.repositories.entities.Notification;
import com.sample.repositories.entities.User;

@RequiredArgsConstructor
@Singleton
public class NotificationServiceImp implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void addNotification(NotificationType type, User user, String redirectTo) {
        Notification notification = new Notification();
        notification.setNotificationType(type);
        notification.setRead(false);
        notification.setRedirectTo(redirectTo);
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> findUnreadNotification(User user) {
        return notificationRepository.findByReadAndUserOrderByCreateDateDesc(false, user);
    }

    @Override
    public void makeNotificationsRead(User user) {
        findUnreadNotification(user)
                .forEach(
                        notification -> {
                            readNotification(notification.getId());
                        });
    }

    private void readNotification(Long id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setRead(true);
            notificationRepository.update(notification);
        }
    }
}
