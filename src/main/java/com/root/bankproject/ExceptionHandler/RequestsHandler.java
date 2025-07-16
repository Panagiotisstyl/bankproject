package com.root.bankproject.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RequestsHandler {

    public <T>Response<T> okReq(T dto){
        return Response.<T>builder()
                .statusCode(HttpStatus.OK.value())
                .data(dto).build();
    }
}
