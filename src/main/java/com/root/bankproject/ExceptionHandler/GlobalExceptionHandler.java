package com.root.bankproject.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Response<String> handleRuntimeException(RuntimeException ex) {
        return Response.<String>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .data(ex.getMessage())
                .build();
    }
}
