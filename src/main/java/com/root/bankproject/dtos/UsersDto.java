package com.root.bankproject.dtos;

import com.root.bankproject.enums.TypeAccount;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersDto {

    private String username;
    private String password;
    private String email;

}
