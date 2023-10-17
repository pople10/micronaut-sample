/* Mohammed Amine AYACHE (C)2022 */
package com.sample.mappers;

import java.util.Optional;

import com.sample.models.requests.UserRequest;
import com.sample.models.responses.UserResponse;
import com.sample.repositories.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "jsr330")
public abstract class UserMapper {
    public abstract UserResponse userToUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    public abstract User userRequestToUser(UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    public abstract User updateUser(UserRequest userRequest, @MappingTarget User old);

    public Optional<UserResponse> userToUserResponseOptional(Optional<User> user) {
        if (!user.isPresent()) return Optional.empty();
        return Optional.of(userToUserResponse(user.get()));
    }
}
