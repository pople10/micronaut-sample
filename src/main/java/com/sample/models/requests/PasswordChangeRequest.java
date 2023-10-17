/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.requests;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Introspected
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmed;
}
