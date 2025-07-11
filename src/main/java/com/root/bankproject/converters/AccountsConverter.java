package com.root.bankproject.converters;

import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Accounts;
import com.root.bankproject.entities.Users;
import com.root.bankproject.services.AccountsService;
import com.root.bankproject.services.UsersService;
import com.root.bankproject.validations.AccountValidation;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountsConverter {

    private final UsersService usersService;
    private final AccountsService accountsService;
    private final AccountValidation accountValidation;

    public Accounts toEntity(AccountsDto accountsDto){
        return toEntity(accountsDto,null);
    }

    public Accounts toEntity(AccountsDto accountsDto, @Nullable Accounts existing){
        var builder=Accounts.builder();

        if(existing!=null){
            builder.id(existing.getId());
        }

        return builder
                .typeAccount(accountsDto.getTypeAccount())
                .description(accountsDto.getDescription())
                .balance(accountsDto.getBalance())
                .users(getList(accountsDto.getIds()))
                .build();
    }

    private List<Users> getList(List<Integer> ids){
        List<Users> list=new ArrayList<>();

        for (Integer id : ids) {
            list.add(usersService.findById(id));

        }
        return list;
    }

    public static List<Integer> getIds(List<Users> ids){
        List<Integer> list=new ArrayList<>();

        for (Users user : ids) {
            list.add(user.getId());
        }
        return list;
    }

    public AccountResponseDto toResponseDto(Accounts account){
        return AccountResponseDto.builder()
                .id(account.getId())
                .typeAccount(account.getTypeAccount())
                .description(account.getDescription())
                .build();
    }

    public List<AccountResponseDto> toDtoList(List<Accounts> accounts){
        return accounts.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    public List<AccountResponseDto> findALlConvert(){
        return toDtoList(accountsService.findAll());
    }

    public AccountResponseDto findByIdConvert(int accId){
        return toResponseDto(accountsService.findById(accId));
    }

    public AccountResponseDto registerAccountConvert(AccountsDto accountsDto){
        return toResponseDto(accountsService.save(toEntity(accountsDto)));
    }

    public void addUserConvert(AccountResponseDto accountResponseDto, int userId){
        AccountsDto dto=accountValidation.validateUsersAccount(accountResponseDto,userId);
        Accounts accToUpdate=toEntity(dto, accountsService.findById(accountResponseDto.getId()));
        accountsService.save(accToUpdate);
    }
}
