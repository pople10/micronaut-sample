/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.responses;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.sample.models.responses.global.TimestampResponse;

@NoArgsConstructor
@Data
@Introspected
public class UserResponse extends TimestampResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String tokenRecord;
}
