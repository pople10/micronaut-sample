/* Mohammed Amine AYACHE (C)2022 */
package com.sample.mappers;

import com.sample.models.responses.NotificationResponse;
import com.sample.repositories.entities.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jsr330")
public abstract class NotificationMapper {
    public abstract NotificationResponse notificationToNotificationResponse(
            Notification notification);
}
