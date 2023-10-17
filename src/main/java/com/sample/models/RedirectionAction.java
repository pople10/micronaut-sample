package com.sample.models;

import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;

@Data
@Introspected
@Builder
public class RedirectionAction {
    private String code;
    private String link;
}
