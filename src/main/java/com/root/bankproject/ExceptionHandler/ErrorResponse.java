package com.root.bankproject.ExceptionHandler;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class ErrorResponse {

    private int statusCode;
    private String message;

}
