package com.root.bankproject.services;

import com.root.bankproject.entities.Accounts;
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

    public List<Accounts> findAll(){
        return accountsRepository.findAll();
    }

    public Accounts findById(int id){
        return accountsRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    public Accounts save(Accounts accounts){
        return accountsRepository.save(accounts);
    }

    public boolean deleteById(int id){
        if(!accountsRepository.existsById(id))
            return false;
        accountsRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    public Optional<Accounts> findByIdWithUsers(int accId) {
        return accountsRepository.findByIdWithUsers(accId);
    }

}
