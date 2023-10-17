/* Mohammed Amine AYACHE (C)2022 */
package com.sample.factories;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.env.Environment;
import io.micronaut.email.javamail.sender.MailPropertiesProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Properties;

@Factory
public class MailFactory {
    @Inject private Environment environment;

    @Singleton
    @Primary
    MailPropertiesProvider createMailPropertiesProvider() {
        return new MailPropertiesProvider() {
            @Override
            public Properties mailProperties() {
                Properties properties = new Properties();
                properties.put(
                        "mail.host", environment.getProperty("mail.host", String.class).orElse(""));
                properties.put(
                        "mail.transport.protocol",
                        environment
                                .getProperty("mail.transport.protocol", String.class)
                                .orElse(""));
                properties.put(
                        "mail.port", environment.getProperty("mail.port", String.class).orElse(""));
                properties.put(
                        "mail.user",
                        environment
                                .getProperty("javamail.authentication.username", String.class)
                                .orElse(""));
                properties.put(
                        "mail.password",
                        environment
                                .getProperty("javamail.authentication.password", String.class)
                                .orElse(""));
                String ssl = environment.getProperty("mail.ssl", String.class).orElse("true");
                properties.put("mail.smtp.ssl.enable", ssl);
                properties.setProperty("mail.smtp.starttls.enable", ssl);
                properties.setProperty(
                        "debug",
                        environment.getProperty("mail.debug", String.class).orElse("false"));
                return properties;
            }
        };
    }
}
