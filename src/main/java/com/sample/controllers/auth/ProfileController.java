/* Mohammed Amine AYACHE (C)2022 */
package com.sample.controllers.auth;

import com.sample.models.requests.PasswordChangeRequest;
import com.sample.models.requests.ProfileChangeRequest;
import com.sample.models.responses.UserResponse;
import com.sample.processes.UserProcess;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import javax.transaction.Transactional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller("/api/auth/")
@Transactional
@Tag(name = "Profile")
@RequiredArgsConstructor
public class ProfileController {
    private final UserProcess userProcess;

    @Get("/me")
    HttpResponse<UserResponse> getMyProfile(Principal principal) {
        return HttpResponse.ok(userProcess.getUserByEmail(principal.getName()));
    }

    @Put("/password/change")
    HttpResponse<Void> changePassword(
            @Body @Valid PasswordChangeRequest request, Principal principal) {
        userProcess.changePassword(request, principal.getName());
        return HttpResponse.ok();
    }

    @Put("/profile/change")
    HttpResponse<Void> changeProfile(
            @Body @Valid ProfileChangeRequest request, Principal principal) {
        userProcess.changeProfile(request, principal.getName());
        return HttpResponse.ok();
    }
}
