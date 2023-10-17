/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.responses;

import com.sample.utils.enumerations.NotificationType;
import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Introspected
public class NotificationResponse {
    private Long id;
    private NotificationType notificationType;
    private String redirectTo;
    private boolean read;
}
