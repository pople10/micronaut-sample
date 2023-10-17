/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.requests;

import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Introspected
public class ResetRequest {
    @Email private String email;

    private String redirectTo;
}
