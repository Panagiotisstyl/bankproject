package com.root.bankproject.dtos;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponseDto {

    private int id;
    private String username;
}
