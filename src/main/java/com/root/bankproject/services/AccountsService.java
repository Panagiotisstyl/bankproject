package com.root.bankproject.services;

import com.root.bankproject.entities.Account;
import com.root.bankproject.entities.User;
import com.root.bankproject.repositories.AccountsRepository;
import com.root.bankproject.validations.AccountValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;
    private final AccountValidation accountValidation;
    private final UsersService usersService;

    public List<Account> findAll(){
        return accountsRepository.findAll();
    }

    public Account findById(int id){
        return accountsRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    public Account save(Account account){
        return accountsRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Optional<Account> findByIdWithUsers(int accId) {
        return accountsRepository.findByIdWithUsers(accId);
    }

    public void addUser(int accountId, int userId){

        Account acc=findById(accountId);
        accountValidation.validateUsersAccount(acc);

        User user=usersService.findById(userId);
        acc.getUsers().add(user);
        accountsRepository.save(acc);

    }


}
