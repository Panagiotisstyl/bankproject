package com.root.bankproject.services;

import com.root.bankproject.entities.Account;
import com.root.bankproject.repositories.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;

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
    public Optional<Accounts> findByIdWithUsers(int accId) {
        return accountsRepository.findByIdWithUsers(accId);
    }

}
