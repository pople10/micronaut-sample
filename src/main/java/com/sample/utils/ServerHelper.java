package com.sample.utils;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.http.server.util.HttpClientAddressResolver;
import io.micronaut.http.server.util.HttpHostResolver;
import io.micronaut.runtime.http.scope.RequestScope;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequestScope
@RequiredArgsConstructor
public class ServerHelper {
    private final HttpHostResolver httpHostResolver;
    private final HttpClientAddressResolver addressResolver;
    public static final String DEFAULT_IP = "0.0.0.0";

    public String getHostName(String defaultValue)
    {
        Optional<HttpRequest<Object>> currentRequest = ServerRequestContext.currentRequest();
        if(!currentRequest.isPresent()) return defaultValue;
        HttpRequest<?> request = currentRequest.get();
        String url = httpHostResolver.resolve(request);
        return url==null?defaultValue:url;
    }

    public String getCurrentIP(String defaultValue)
    {
        Optional<HttpRequest<Object>> currentRequest = ServerRequestContext.currentRequest();
        if(!currentRequest.isPresent()) return defaultValue;
        HttpRequest<?> request = currentRequest.get();
        return addressResolver.resolve(request);
    }

    public String getCurrentIP(){
        return getCurrentIP(DEFAULT_IP);
    }

    public String getHostName(){
        return getHostName(null);
    }
}
