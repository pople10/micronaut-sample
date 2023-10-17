/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers.auth;

import com.sample.models.requests.ResetChangeRequest;
import com.sample.models.requests.ResetRequest;
import com.sample.models.requests.RoleRequest;
import com.sample.models.requests.UserRequest;
import com.sample.processes.UserProcess;
import com.sample.security.CustomAccessRefreshTokenGenerator;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collections;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sample.models.requests.*;
import com.sample.repositories.entities.Role;
import com.sample.repositories.entities.User;

@Controller("/auth/register")
@Transactional
@Tag(name = "Register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserProcess userProcess;
    private final CustomAccessRefreshTokenGenerator customAccessRefreshTokenGenerator;

    @Post(consumes = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED})
    HttpResponse<AccessRefreshToken> createUser(@Body @Valid UserRequest userRequest) {
        userRequest.setRoles(Collections.singleton(new RoleRequest("USER")));
        User response = userProcess.addUser(userRequest);
        AccessRefreshToken loginDetails =
                customAccessRefreshTokenGenerator
                        .generate(
                                Authentication.build(
                                        response.getEmail(),
                                        response.getRoles().stream()
                                                .map(Role::getCode)
                                                .collect(Collectors.toSet()),
                                        CollectionUtils.mapOf(
                                                "firstName",
                                                response.getFirstName(),
                                                "lastName",
                                                response.getLastName(),
                                                "email",
                                                response.getEmail())))
                        .orElse(null);
        return HttpResponse.created(loginDetails);
    }

    @Post("/reset")
    HttpResponse<Void> sendResetPassword(@Body @Valid ResetRequest request) {
        userProcess.sendResetRequest(request);
        return HttpResponse.ok();
    }

    @Put("/reset/change")
    HttpResponse<Void> resetPassword(@Body @Valid ResetChangeRequest request) {
        userProcess.changePassword(request.getVkey(), request.getPassword());
        return HttpResponse.ok();
    }
}
