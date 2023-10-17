/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers;

import com.sample.models.requests.UserRequest;
import com.sample.models.responses.UserResponse;
import com.sample.processes.UserProcess;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller("/api/user")
@Transactional
@Tag(name = "User")
@RequiredArgsConstructor
public class UserController {
    private final UserProcess userProcess;

    @Put("/{id:[0-9]+}")
    HttpResponse<UserResponse> updateUser(@Body UserRequest userRequest, Long id) {
        return HttpResponse.ok(userProcess.updateUser(userRequest, id));
    }

    @Get("/{id:[0-9]+}")
    HttpResponse<UserResponse> getUser(Long id) {
        return HttpResponse.ok(userProcess.getUserById(id));
    }
}
