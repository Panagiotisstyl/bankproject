package com.root.bankproject.validations;


import com.root.bankproject.entities.Account;
import com.root.bankproject.enums.TypeAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class AccountValidation {


    public void validateUsersAccount(Account account) {

        if (account.getTypeAccount() == TypeAccount.SINGLE && !account.getUsers().isEmpty()) {
            throw new RuntimeException("Cannot add user, account type is single");
        }


    }
}