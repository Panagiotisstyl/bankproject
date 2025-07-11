package com.root.bankproject.validations;


import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Accounts;
import com.root.bankproject.entities.Users;
import com.root.bankproject.enums.TypeAccount;
import com.root.bankproject.services.AccountsService;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component
public class AccountValidation {

    private final AccountsService accountsService;
    private final UsersService usersService;

    public AccountsDto validateUsersAccount(AccountResponseDto dto, int userId){

            Accounts account= accountsService.findById(dto.getId());

            if(account.getTypeAccount()== TypeAccount.SINGLE && !account.getUsers().isEmpty()){
                throw new RuntimeException("Cannot add user, account type is single");
            }
            List<Users> newLIst=new ArrayList<>();

            newLIst.add(usersService.findById(userId));

            return  AccountsDto.builder()
                    .typeAccount(account.getTypeAccount())
                    .description(account.getDescription())
                    .balance(account.getBalance())
                    .ids(AccountsConverter.getIds(newLIst))
                    .build();

    }
}
