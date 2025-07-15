package com.root.bankproject.converters;

import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Account;
import com.root.bankproject.services.UsersService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class AccountsConverter {

    private final UsersService usersService;

    public Account toEntity(AccountsDto accountsDto){
        return toEntity(accountsDto,null);
    }

    public Account toEntity(AccountsDto accountsDto, @Nullable Account existing){

        return Account.builder()
                .id(nonNull(existing)?existing.getId():null)
                .typeAccount(accountsDto.getTypeAccount())
                .description(accountsDto.getDescription())
                .balance(accountsDto.getBalance())
                .users(accountsDto.getIds().stream().map(usersService::findById).collect(Collectors.toList()))
                .build();
    }

    public AccountResponseDto toResponseDto(Account account){
        return AccountResponseDto.builder()
                .id(account.getId())
                .typeAccount(account.getTypeAccount())
                .description(account.getDescription())
                .build();
    }

    public List<AccountResponseDto> toDtoList(List<Account> accounts){
        return accounts.stream().map(this::toResponseDto).collect(Collectors.toList());
    }
}
