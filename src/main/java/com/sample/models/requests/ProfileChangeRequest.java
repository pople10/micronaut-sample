/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.requests;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Introspected
public class ProfileChangeRequest {
    private String email;
    private String lastname;
    private String firstname;
}
