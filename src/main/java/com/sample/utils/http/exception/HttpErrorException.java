package com.sample.utils.http.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class HttpErrorException extends Exception{
    private static final ObjectMapper mapper = new ObjectMapper();
    private Integer code;
    private String reason;
    private Object body;
    private Class<?> type;

    public HttpErrorException(HttpResponse response,Class errorType) {
        this.type = errorType;
        if(response==null)
            return;
        this.code = response.code();
        this.reason = response.reason();
        this.body = response.getBody(errorType).orElse(null);
    }

    public <T> T getBody(Class<T> type){
        if(this.type==null)
            return null;
        return mapper.convertValue(this.body,type);
    }

    public <T> T getBodyDefault(){
        return (T) getBody(this.type);
    }
}
