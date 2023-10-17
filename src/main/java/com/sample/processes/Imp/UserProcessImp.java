/* Mohammed Amine AYACHE (C)2022 */
package com.sample.processes.Imp;

import com.sample.mappers.UserMapper;
import com.sample.models.requests.*;
import com.sample.models.responses.UserResponse;
import com.sample.processes.UserProcess;
import com.sample.utils.HashUtils;
import com.sample.services.RoleService;
import com.sample.services.UserService;
import com.sample.utils.enumerations.EntityStatus;
import jakarta.inject.Singleton;

import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import com.sample.exceptions.AuthenticationFailedException;
import com.sample.exceptions.EntityNotFoundException;
import com.sample.exceptions.GenericApplicationException;
import com.sample.exceptions.ValidationException;
import com.sample.models.requests.*;
import com.sample.models.responses.*;
import com.sample.repositories.entities.User;
import com.sample.services.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Singleton
public class UserProcessImp implements UserProcess {
    private static final Logger logger = LoggerFactory.getLogger(UserProcess.class);
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Override
    public User addUser(UserRequest userRequest) {
        userRequest.setPassword(userService.encryptPasswordAndCheckEmail(userRequest));
        User user = userMapper.userRequestToUser(userRequest);
        assignRoles(user,userRequest.getRoles());
        User out = userService.createUser(user);
        userService.sendMail(userRequest);
        return out;
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, Long id) {

        logger.debug("Updating User from Request: {} with id {}", userRequest, id);
        User user =
                userService
                        .findUserById(id)
                        .orElseThrow(
                                () -> {
                                    throw new EntityNotFoundException("user_not_found");
                                });
        userRequest.setPassword(userService.encryptPasswordAndCheckEmail(userRequest, id));
        user = userMapper.updateUser(userRequest, user);
        assignRoles(user, userRequest.getRoles());
        return userMapper.userToUserResponse(userService.updateUser(user));
    }

    @Override
    public UserResponse getUserById(Long id) {
        return userMapper.userToUserResponse(
                userService
                        .findUserById(id)
                        .orElseThrow(
                                () -> {
                                    throw new EntityNotFoundException("user_not_found");
                                }));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userService
                .findAllUsers()
                // to benefit from optimization
                .parallelStream()
                .map(userMapper::userToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        logger.debug("Deleting User with id {}", id);
        userService.deleteUser(id);
    }

    @Override
    public boolean isCredentialCorrect(User user, String password) {
        return userService.checkPassword(password, user.getPassword());
    }

    @Override
    public Optional<User> getUserByCredentials(String email) {
        return userService.findUserByEmail(email);
    }

    @Override
    public UserResponse getUserByEmail(String email, boolean auth) {
        GenericApplicationException exception = new EntityNotFoundException("user_not_found");
        if (auth) exception = new AuthenticationFailedException();
        GenericApplicationException finalException = exception;
        return userMapper.userToUserResponse(
                userService
                        .findUserByEmail(email)
                        .orElseThrow(
                                () -> {
                                    throw finalException;
                                }));
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return getUserByEmail(email, true);
    }

    @Override
    public void sendResetRequest(ResetRequest request) {
        userService.resetPasswordRequest(request.getEmail(), request.getRedirectTo());
    }

    @Override
    public void changePassword(String vkey, String newPassword) {
        userService.changePassword(vkey, newPassword);
    }

    @Override
    public void changePassword(PasswordChangeRequest passwordChangeRequest, String email) {
        if (!passwordChangeRequest
                .getNewPassword()
                .equals(passwordChangeRequest.getNewPasswordConfirmed()))
            throw new ValidationException("password_not_confirmed");
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (!optionalUser.isPresent()) throw new EntityNotFoundException("user_not_found");
        User user = optionalUser.get();
        if (!isCredentialCorrect(user, passwordChangeRequest.getOldPassword()))
            throw new ValidationException("old_password_incorrect");
        userService.changePassword(user, passwordChangeRequest.getNewPassword());
    }

    @Override
    public void changeProfile(ProfileChangeRequest request, String email) {
        Optional<User> optionalUser = userService.findUserByEmail(email);
        if (!optionalUser.isPresent()) throw new EntityNotFoundException("user_not_found");
        User user = optionalUser.get();
        if (StringUtils.isNotEmpty(request.getEmail())) {
            this.userService.checkMail(user, request.getEmail());
            user.setEmail(request.getEmail());
        }
        if (StringUtils.isNotEmpty(request.getLastname())) user.setLastName(request.getLastname());
        if (StringUtils.isNotEmpty(request.getFirstname()))
            user.setFirstName(request.getFirstname());
        userService.updateUser(user);
    }

    @Override
    public void updateStatus(Long id, EntityStatus status) {
        Optional<User> optionalUser = userService.findUserById(id);
        if (!optionalUser.isPresent()) throw new EntityNotFoundException("user_not_found");
        User user = optionalUser.get();
        user.setStatus(status);
        userService.updateUser(user);
    }

    @Override
    public void lockUser(Long id) {
        updateStatus(id, EntityStatus.HOLD);
    }

    private void assignRoles(User user, Set<RoleRequest> roles) {
        if (roles == null || roles.isEmpty()) return;
        user.setRoles(new HashSet<>());
        roles.forEach(e -> user.addRole(roleService.getRoleByCode(e.getCode())));
    }
}
