package com.root.bankproject.dtos;

import com.root.bankproject.enums.TypeAccount;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountResponseDto {

    private int id;
    private TypeAccount typeAccount;
    private String description;
    private List<Integer> userIds;
    private double balance;
}
