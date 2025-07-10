package com.root.bankproject.dtos;

import com.root.bankproject.enums.TypeAccount;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountResponseDto {
    private int id;
    private TypeAccount typeAccount;
    private String description;
}
