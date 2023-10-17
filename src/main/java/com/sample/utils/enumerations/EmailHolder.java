/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils.enumerations;

import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EmailHolder {
    ACCOUNT_CREATED("welcome", "account_created"),
    RESET_PASSWORD("reset", "reset_password"),
    REQUEST_APPROVE_CREATED("approve", "approve_request_created"),
    REQUEST_APPROVED("approved", "approved_request");
    private final String templateName;
    private final String subject;
    public static final Map<String, String> approveRequestCreatedData = Collections.emptyMap();

    public static final Map<String, String> approvedRequestData = Collections.emptyMap();
}
