package com.root.bankproject.command;

import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Account;
import com.root.bankproject.services.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountCommand {

    private final AccountsService accountsService;
    private final AccountsConverter accountsConverter;
    private final KafkaTemplate<String, AccountResponseDto> kafkaTemplateAccountResponse;


    public List<AccountResponseDto> findALl(){
        return accountsConverter.toDtoList(accountsService.findAll());
    }

    public AccountResponseDto findById(int accId){
        return accountsConverter.toResponseDto(accountsService.findById(accId));
    }

    public AccountResponseDto registerAccount(AccountsDto accountsDto){
        AccountResponseDto accDto=accountsConverter.toResponseDto(accountsService.save(accountsConverter.toEntity(accountsDto)));
        kafkaTemplateAccountResponse.send("account.created", accDto);
        return accDto;
    }

    public AccountResponseDto addUser(int accountId, int userId){
        AccountResponseDto accountResponseDto=accountsConverter.toResponseDto(accountsService.addUser(accountId,userId));
        kafkaTemplateAccountResponse.send("user.addedToAccount", accountResponseDto);
        return accountResponseDto;
    }

    public AccountResponseDto depositMoney(double balance, int accountId) {
        Account account=accountsService.findById(accountId);
        account.depositBalance(balance);
        AccountResponseDto accDto=accountsConverter.toResponseDto(accountsService.save(account));
        kafkaTemplateAccountResponse.send("deposit.money", accDto);
        return accDto;

    }

    public AccountResponseDto withdrawMoney(double moneyToWithdraw, int accountId) {
        Account account=accountsService.findById(accountId);
        account.withdrawal(moneyToWithdraw);
        AccountResponseDto accDto=accountsConverter.toResponseDto(accountsService.save(account));
        kafkaTemplateAccountResponse.send("withdraw.money", accDto);
        return accDto;
    }

}
