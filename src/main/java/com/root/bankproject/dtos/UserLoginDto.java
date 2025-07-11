package com.root.bankproject.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginDto {
    private String username;
    private String password;
}
