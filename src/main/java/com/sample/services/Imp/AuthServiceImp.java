/* Mohammed Amine AYACHE (C)2023 */
package com.sample.services.Imp;

import com.sample.security.CustomSecurityService;
import com.sample.services.AuthService;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import com.sample.exceptions.AuthenticationFailedException;
import com.sample.repositories.UserRepository;
import com.sample.repositories.entities.User;

@Singleton
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final CustomSecurityService securityService;
    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        String username =
                securityService
                        .username()
                        .orElseThrow(
                                () -> {
                                    throw new AuthenticationFailedException();
                                });
        return userRepository
                .findFirstByEmail(username)
                .orElseThrow(
                        () -> {
                            throw new AuthenticationFailedException();
                        });
    }

    @Override
    public User getCurrentUserSafe() {
        try {
            return getCurrentUser();
        } catch (Throwable throwable) {
            return null;
        }
    }
}
