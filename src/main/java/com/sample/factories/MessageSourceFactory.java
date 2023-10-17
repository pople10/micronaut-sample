/* Mohammed Amine AYACHE (C)2022 */
package com.sample.factories;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import jakarta.inject.Singleton;

@Factory
class MessageSourceFactory {
    @Singleton
    MessageSource createMessageSource() {
        return new ResourceBundleMessageSource("messages");
    }

    @Singleton
    MessageSource createValidationSource() {
        return new ResourceBundleMessageSource("javax_validation");
    }

    @Singleton
    MessageSource createPaymentCredentials() {
        return new ResourceBundleMessageSource("payment");
    }
}
