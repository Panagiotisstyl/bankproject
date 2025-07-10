package com.root.bankproject.dtos;

import com.root.bankproject.enums.TypeAccount;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountsDto {

    private TypeAccount typeAccount;
    private String description;
    private double balance;
    private List<Integer> ids;

}
