package com.root.bankproject.converters;

import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Account;
import com.root.bankproject.entities.User;
import com.root.bankproject.services.UsersService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountsConverter {

    private final UsersService usersService;

    public Account toEntity(AccountsDto accountsDto){
        return toEntity(accountsDto,null);
    }

    public Account toEntity(AccountsDto accountsDto, @Nullable Account existing){
        var builder= Account.builder();

        if(existing!=null){
            builder.id(existing.getId());
        }

        return builder
                .typeAccount(accountsDto.getTypeAccount())
                .description(accountsDto.getDescription())
                .balance(accountsDto.getBalance())
                .users(getList(accountsDto.getIds()))
                .build();
    }

    private List<User> getList(List<Integer> ids){
        List<User> list=new ArrayList<>();

        for (Integer id : ids) {
            list.add(usersService.findById(id));

        }
        return list;
    }

    public static List<Integer> getIds(List<User> ids){
        List<Integer> list=new ArrayList<>();

        for (User user : ids) {
            list.add(user.getId());
        }
        return list;
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
