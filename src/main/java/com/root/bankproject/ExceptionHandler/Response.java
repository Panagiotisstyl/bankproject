package com.root.bankproject.ExceptionHandler;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Response<T> {

    private int statusCode;
    private T data;
}
