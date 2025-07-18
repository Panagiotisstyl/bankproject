package com.root.bankproject.factories;

import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.entities.Account;
import com.root.bankproject.enums.TypeAccount;
import com.root.bankproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountFactory {

    private final UsersRepository usersRepository;
    public Account createAccount(TypeAccount typeAccount, String desc, double balance){
        return Account.builder()
                .typeAccount(typeAccount)
                .description(desc)
                .balance(balance)
                .users(List.of(usersRepository.save(UserFactory.createUser("pan","panstyl@email.com", BcryptHashing.hashPassword("asa"))),usersRepository.save(UserFactory.createUser("pana","panstyli@email.com",BcryptHashing.hashPassword("asas")))))
                .build();
    }

    public AccountsDto createAccountsDto(TypeAccount typeAccount, String desc, double balance){

        return AccountsDto.builder()
                .typeAccount(typeAccount)
                .description(desc)
                .balance(balance)
                .ids(List.of(usersRepository.save(UserFactory.createUser("pan","panstyl@email.com","asa")).getId(),usersRepository.save(UserFactory.createUser("pana","panstyli@email.com",BcryptHashing.hashPassword("asas"))).getId()))
                .build();
    }

    public AccountsDto createAccountsDtoWrongUser(TypeAccount typeAccount, String desc,double balance){
        return AccountsDto.builder()
                .ids(List.of(1223,1022))
                .typeAccount(typeAccount)
                .description(desc)
                .balance(balance)
                .build();
    }
}
