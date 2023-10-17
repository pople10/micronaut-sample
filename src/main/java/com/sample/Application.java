/* Mohammed Amine AYACHE (C)2022 */
package com.sample;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import org.slf4j.bridge.SLF4JBridgeHandler;

@OpenAPIDefinition(info = @Info(title = "backend", version = "1.0"))
public class Application {
    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Micronaut.run(Application.class, args);
    }
}
