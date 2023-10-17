/* Mohammed Amine AYACHE (C)2023 */
package com.sample.security;

import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.generator.RefreshTokenGenerator;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.generator.AccessRefreshTokenGenerator;
import io.micronaut.security.token.jwt.generator.AccessTokenConfiguration;
import io.micronaut.security.token.jwt.generator.DefaultAccessRefreshTokenGenerator;
import io.micronaut.security.token.jwt.generator.claims.ClaimsGenerator;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.security.token.jwt.render.TokenRenderer;
import jakarta.inject.Singleton;
import java.util.Optional;

@Singleton
@Primary
public class CustomAccessRefreshTokenGenerator extends DefaultAccessRefreshTokenGenerator
        implements AccessRefreshTokenGenerator {

    public CustomAccessRefreshTokenGenerator(
            AccessTokenConfiguration accessTokenConfiguration,
            TokenRenderer tokenRenderer,
            TokenGenerator tokenGenerator,
            BeanContext beanContext,
            @Nullable RefreshTokenGenerator refreshTokenGenerator,
            ClaimsGenerator claimsGenerator,
            ApplicationEventPublisher eventPublisher) {
        super(
                accessTokenConfiguration,
                tokenRenderer,
                tokenGenerator,
                beanContext,
                refreshTokenGenerator,
                claimsGenerator,
                eventPublisher);
    }

    @Override
    public Optional<AccessRefreshToken> generate(Authentication authentication) {
        Optional<AccessRefreshToken> previous = super.generate(authentication);
        if (previous == null || !previous.isPresent()) return previous;
        AccessRefreshToken model = previous.get();
        CustomAccessRefreshToken customAccessRefreshToken =
                new CustomAccessRefreshToken(model, authentication.getAttributes());
        return Optional.ofNullable(customAccessRefreshToken);
    }
}
