/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.responses;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Introspected
public class RoleResponse {
    private Long id;

    private String code;
}
