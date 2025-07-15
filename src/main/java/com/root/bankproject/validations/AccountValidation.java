package com.root.bankproject.validations;


import com.root.bankproject.entities.Account;
import com.root.bankproject.enums.TypeAccount;
import com.root.bankproject.repositories.AccountsRepository;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class AccountValidation {

    private final AccountsRepository accountsRepository;
    private final UsersService usersService;

    public void validateUsersAccount(int accId, int userId){

            Account account= accountsRepository.findById(accId).orElseThrow(()->new RuntimeException("User not found"));
            usersService.findById(userId);

            if(account.getTypeAccount()== TypeAccount.SINGLE && !account.getUsers().isEmpty()){
                throw new RuntimeException("Cannot add user, account type is single");
            }



    }
}
