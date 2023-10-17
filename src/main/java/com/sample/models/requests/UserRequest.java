/* Mohammed Amine AYACHE (C)2022 */
package com.sample.models.requests;

import io.micronaut.core.annotation.Introspected;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Introspected
public class UserRequest {

    @Size(max = 72)
    private String firstName;

    @Size(max = 72)
    private String lastName;

    @Email private String email;

    private String phone;

    private String password;

    private Set<RoleRequest> roles;
}
