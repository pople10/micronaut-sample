/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.requests;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Introspected
@AllArgsConstructor
public class RoleRequest {
    private String code;
}
