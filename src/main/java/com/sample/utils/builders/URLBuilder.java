/* Mohammed Amine AYACHE (C)2023 */
package com.sample.utils.builders;

import io.micronaut.context.env.Environment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class URLBuilder {
    private String url;
    private Map<String, String> attributes;

    @Inject private Environment environment;

    public URLBuilder initBuilder() {
        url = null;
        attributes = new HashMap<>();
        return this;
    }

    public URLBuilder data(Map<String, String> data) {
        attributes = data;
        return this;
    }

    public URLBuilder url(String url, boolean env) {
        if (env) url = environment.get(url, String.class).orElse("");
        this.url = url;
        return this;
    }

    public String build() {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            this.url =
                    this.url.replaceAll(
                            String.format("\\{%s\\}", entry.getKey()), entry.getValue());
        }
        return this.url;
    }
}
