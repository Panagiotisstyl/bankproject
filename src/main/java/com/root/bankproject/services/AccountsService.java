package com.root.bankproject.services;

import com.root.bankproject.entities.Accounts;
import com.root.bankproject.repositories.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void addUser(Accounts accounts){

        save(accounts);
    }

    //TODO: delete this. its unused
    public boolean deleteById(int id){
        if(!accountsRepository.existsById(id))
            return false;
        accountsRepository.deleteById(id);
        return true;
    }

}
