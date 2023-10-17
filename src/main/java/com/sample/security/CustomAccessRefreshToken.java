/* Mohammed Amine AYACHE (C)2023 */
package com.sample.security;

import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import java.util.Map;
import lombok.Data;

@Data
public class CustomAccessRefreshToken extends AccessRefreshToken {
    private Map<String, Object> extra;

    public CustomAccessRefreshToken(AccessRefreshToken old, Map<String, Object> extra) {
        super(old.getAccessToken(), old.getRefreshToken(), old.getTokenType(), old.getExpiresIn());
        this.extra = extra;
    }
}
