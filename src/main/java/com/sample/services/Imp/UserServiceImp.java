/* Mohammed Amine AYACHE (C)2022 */
package com.sample.services.Imp;

import com.sample.models.requests.UserRequest;
import com.sample.utils.HashUtils;
import com.sample.services.UserService;
import com.sample.utils.EmailService;
import com.sample.utils.enumerations.EmailHolder;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import com.sample.exceptions.EntityNotFoundException;
import com.sample.exceptions.GenericApplicationException;
import com.sample.exceptions.ValidationException;
import com.sample.repositories.UserRepository;
import com.sample.repositories.entities.Role;
import com.sample.repositories.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Singleton
public class UserServiceImp implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(
                userRepository
                        .findById(id)
                        .orElseThrow(
                                () -> {
                                    throw new EntityNotFoundException("user_not_found");
                                }));
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String encryptPasswordAndCheckEmail(UserRequest userRequest) {
        return encryptPasswordAndCheckEmail(userRequest, null);
    }

    @Override
    public boolean checkPassword(String password, String encoded) {
        return bCryptPasswordEncoder.matches(password, encoded);
    }

    @Override
    public void sendMail(UserRequest userRequest) {
        emailService.sendMailTemplate(
                EmailHolder.ACCOUNT_CREATED,
                CollectionUtils.mapOf("fullName", userRequest.getFirstName()),
                Arrays.asList(userRequest.getEmail()));
    }

    @Override
    public void changePassword(String vKey, String newPassword) {
        Optional<User> optionalUser = userRepository.findFirstByVkey(vKey);
        if (!optionalUser.isPresent()) throw new ValidationException("could_not_find_vkey");
        User user = optionalUser.get();
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setVkey(HashUtils.randomAlphanumric());
        userRepository.save(user);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setVkey(HashUtils.randomAlphanumric());
        userRepository.save(user);
    }

    @Override
    public void resetPasswordRequest(String email, String redirect) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setVkey(HashUtils.randomAlphanumric());
            userRepository.save(user);
            if (!redirect.endsWith("/")) redirect = redirect + "/";
            try {
                emailService.sendMailTemplateNonAsync(
                        EmailHolder.RESET_PASSWORD,
                        CollectionUtils.mapOf(
                                "link", redirect + user.getVkey(), "fullName", user.getFirstName()),
                        Collections.singletonList(email));
            } catch (Throwable throwable) {
                throw new GenericApplicationException("email_not_sent");
            }
        }
    }

    @Override
    public String encryptPasswordAndCheckEmail(UserRequest userRequest, Long id) {
        logger.debug("Adding User from Request: {}", userRequest);
        logger.debug("Encrypting password");
        String password = bCryptPasswordEncoder.encode(userRequest.getPassword());
        logger.debug("Encryption is done");
        Optional<User> user = userRepository.findFirstByEmail(userRequest.getEmail());
        boolean cond =
                user.isPresent() ? (id == null ? true : !user.get().getId().equals(id)) : false;
        if (cond) throw new ValidationException("email_already_exist");
        return password;
    }

    @Override
    public void checkMail(User userCurr, String newEmail) {
        Optional<User> user = userRepository.findFirstByEmail(newEmail);
        boolean cond =
                user.isPresent()
                        ? (userCurr.getId() == null
                                ? true
                                : !user.get().getId().equals(userCurr.getId()))
                        : false;
        if (cond) throw new ValidationException("email_already_exist");
    }

    @Override
    public List<User> findUsersByKeyword(String keyword) {
        return userRepository.findByEmailOrFirstNameContainsOrLastNameContains(
                keyword, keyword, keyword);
    }

    @Override
    public User findUserByEmailOrDie(String email) {
        return findUserByEmail(email)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("user_not_found");
                        });
    }

    @Override
    public Page<User> getAllByRolePage(Role role, Pageable pageable) {
        return userRepository.findAllByRole(role, pageable);
    }
}
