/* Mohammed Amine AYACHE (C)2022 */
package com.sample.loaders;

import com.sample.models.requests.RoleRequest;
import com.sample.models.requests.UserRequest;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import jakarta.inject.Singleton;
import java.util.*;
import lombok.RequiredArgsConstructor;
import com.sample.exceptions.GenericApplicationException;
import com.sample.processes.UserProcess;

@Singleton
@Requires(notEnv = Environment.TEST)
@RequiredArgsConstructor
public class DataLoader<ServiceStartedEvent>
        implements ApplicationEventListener<ServiceStartedEvent> {
    private final UserProcess userProcess;

    private static boolean added = false;

    @Override
    public void onApplicationEvent(ServiceStartedEvent event) {
        if (added) return;
        try {
            // Inject data from a file here
        } catch (Exception e) {
            if (!(e instanceof GenericApplicationException)) throw e;
        }
        added = true;
    }
}
