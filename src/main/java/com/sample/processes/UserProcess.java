/* Mohammed Amine AYACHE (C)2022 */
package com.sample.processes;

import java.util.List;
import java.util.Optional;

import com.sample.models.requests.PasswordChangeRequest;
import com.sample.models.requests.ProfileChangeRequest;
import com.sample.models.requests.ResetRequest;
import com.sample.models.requests.UserRequest;
import com.sample.models.responses.UserResponse;
import com.sample.utils.enumerations.EntityStatus;
import com.sample.models.requests.*;
import com.sample.models.responses.*;
import com.sample.repositories.entities.User;

public interface UserProcess {
    User addUser(UserRequest userRequest);

    UserResponse updateUser(UserRequest userRequest, Long id);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);

    boolean isCredentialCorrect(User user, String password);

    Optional<User> getUserByCredentials(String email);

    UserResponse getUserByEmail(String email, boolean auth);

    UserResponse getUserByEmail(String email);

    void sendResetRequest(ResetRequest request);

    void changePassword(String vkey, String newPassword);

    void changePassword(PasswordChangeRequest passwordChangeRequest, String email);

    void changeProfile(ProfileChangeRequest request, String email);

    void lockUser(Long id);

    void updateStatus(Long id, EntityStatus status);
}
