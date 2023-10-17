package com.sample.utils.http;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import com.sample.utils.http.exception.HttpErrorException;

@Singleton
@RequiredArgsConstructor
public class BlockingHttpClient {
    private final HttpClient httpClient;

    private boolean isSuccess(HttpResponse response){
        return response==null?false:isSuccess(response.status());
    }

    private boolean isSuccess(HttpStatus status){
        return status.getCode()>199&&status.getCode()<300;
    }

    public <T,O,G> T post(String url, G body,Class<T> successType,Class<O> errorType) throws HttpErrorException {
        HttpResponse<T> response = null;
        try{
            response = httpClient.toBlocking()
                    .exchange(HttpRequest.POST(url,body), Argument.of(successType),Argument.STRING);
        }catch (HttpClientResponseException e) {
            throw new HttpErrorException(e.getResponse(),errorType);
        }
        if(isSuccess(response)){
            return response.body();
        }
        throw new HttpErrorException(response,errorType);
    }
}
