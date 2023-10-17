/* Mohammed Amine AYACHE (C)2022 */
package com.sample.services;

import com.sample.models.requests.UserRequest;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import java.util.List;
import java.util.Optional;

import com.sample.repositories.entities.Role;
import com.sample.repositories.entities.User;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long id);

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmail(String email);

    List<User> findAllUsers();

    String encryptPasswordAndCheckEmail(UserRequest userRequest, Long id);

    String encryptPasswordAndCheckEmail(UserRequest userRequest);

    boolean checkPassword(String password, String encoded);

    void sendMail(UserRequest userRequest);

    void changePassword(String vKey, String newPassword);

    void changePassword(User user, String newPassword);

    void resetPasswordRequest(String email, String url);

    void checkMail(User user, String newEmail);

    List<User> findUsersByKeyword(String keyword);

    User findUserByEmailOrDie(String email);

    Page<User> getAllByRolePage(Role role, Pageable pageable);
}
