/* Mohammed Amine AYACHE (C)2022 */
package com.sample.utils;

import static io.micronaut.views.ViewUtils.EXTENSION_SEPARATOR;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.beans.BeanMap;
import io.micronaut.core.io.Writable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.views.ViewsConfiguration;
import io.micronaut.views.ViewsRenderer;
import io.micronaut.views.exceptions.ViewRenderingException;
import io.micronaut.views.velocity.VelocityViewsRendererConfiguration;
import io.micronaut.views.velocity.VelocityViewsRendererConfigurationProperties;
import jakarta.inject.Singleton;
import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;

@Requires(
        property = VelocityViewsRendererConfigurationProperties.PREFIX + ".enabled",
        notEquals = "false")
@Requires(classes = VelocityEngine.class)
@Singleton
@RequiredArgsConstructor
public class VelocityViewsRenderer implements ViewsRenderer {
    protected VelocityEngine velocityEngine = initializeVelocityEngine();
    protected final ViewsConfiguration viewsConfiguration;
    protected final VelocityViewsRendererConfiguration velocityConfiguration;

    private final String FILE_SEPARATOR = File.separator;

    public String render(String view, Object data) throws Throwable {
        Writable writable = render(view, data, ServerRequestContext.currentRequest().orElse(null));
        StringWriter sw = new StringWriter();
        writable.writeTo(sw);
        return sw.toString();
    }

    @Override
    public @NotNull Writable render(
            @NotNull String view, @Nullable Object data, HttpRequest request) {
        return (writer) -> {
            Map context = context(data);
            final VelocityContext velocityContext = new VelocityContext(context);
            String viewName = viewName(view);
            try {
                velocityEngine.mergeTemplate(
                        viewName, StandardCharsets.UTF_8.name(), velocityContext, writer);
            } catch (ResourceNotFoundException
                    | ParseErrorException
                    | MethodInvocationException e) {
                throw new ViewRenderingException(
                        "Error rendering Velocity view [" + viewName + "]: " + e.getMessage(), e);
            }
        };
    }

    @Override
    public boolean exists(@NotNull String viewName) {
        try {
            velocityEngine.getTemplate(viewName(viewName));
        } catch (ResourceNotFoundException | ParseErrorException e) {
            return false;
        }
        return true;
    }

    private VelocityEngine initializeVelocityEngine() {
        final Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty(
                "class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityEngine(p);
    }

    private Map context(@Nullable Object data) {
        if (data == null) {
            return new HashMap<>();
        }
        if (data instanceof Map) {
            return (Map) data;
        }
        return BeanMap.of(data);
    }

    private String viewName(final String name) {
        final StringBuilder sb = new StringBuilder();
        if (viewsConfiguration.getFolder() != null) {
            sb.append(viewsConfiguration.getFolder());
            if (!viewsConfiguration.getFolder().endsWith(FILE_SEPARATOR)) sb.append(FILE_SEPARATOR);
        }
        sb.append(name.replace("/", FILE_SEPARATOR));
        final String extension = extension();
        if (!name.endsWith(extension)) {
            sb.append(extension);
        }
        return sb.toString();
    }

    private String extension() {
        String sb = EXTENSION_SEPARATOR + velocityConfiguration.getDefaultExtension();
        return sb;
    }
}
