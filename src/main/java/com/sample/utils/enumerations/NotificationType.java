/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum NotificationType {
    MESSAGE_RECEIVED("message_received"),
    REQUEST_ADDED("request_added"),
    REQUEST_APPROVED("request_approved");
    private final String message;
}
