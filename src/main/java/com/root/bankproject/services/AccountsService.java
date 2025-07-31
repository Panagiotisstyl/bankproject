package com.root.bankproject.services;

import com.root.bankproject.entities.Account;
import com.root.bankproject.entities.User;
import com.root.bankproject.repositories.AccountsRepository;
import com.root.bankproject.validations.AccountValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountsService {

    private final AccountsRepository accountsRepository;
    private final AccountValidation accountValidation;
    private final UsersService usersService;

    @Cacheable(value="accountList")
    public List<Account> findAll(){
        List<Account> accountList = accountsRepository.findAll();
        return accountList.stream()
                .map(this::accountWithUser)
                .collect(Collectors.toList());
    }

    @Cacheable(value="account",key="#id")
    public Account findById(int id){
        Account acc=accountsRepository.findById(id).orElseThrow(()->new RuntimeException("Account not found"));
        return accountWithUser(acc);
    }

    @CachePut(value="account",key="#account.id")
    @CacheEvict(value = "accountList", allEntries = true)
    public Account save(Account account){
        return accountsRepository.save(account);
    }

    @CachePut(value="account",key="#accountId")
    @CacheEvict(value = "accountList", allEntries = true)
    public Account addUser(int accountId, int userId){

        Account acc=accountsRepository.findById(accountId).orElseThrow(()->new RuntimeException("Account not found"));
        accountValidation.validateUsersAccount(acc);

        User user=usersService.findById(userId);
        acc.getUsers().add(user);
        return accountsRepository.save(acc);

    }

    private Account accountWithUser(Account acc){
        List<User> users = usersService.findByAccountId(acc.getId());
        return Account.builder()
                .id(acc.getId())
                .typeAccount(acc.getTypeAccount())
                .description(acc.getDescription())
                .users(users)
                .balance(acc.getBalance())
                .build();
    }
}
