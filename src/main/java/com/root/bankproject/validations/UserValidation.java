package com.root.bankproject.validations;


import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.encryption.TokenFactory;
import com.root.bankproject.entities.Users;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Component
public class UserValidation {

    private final UsersService usersService;

    public String validateUser(@RequestBody UsersDto usersDto){
        Users user=usersService.findByEmail(usersDto.getEmail());
        if(BcryptHashing.verifyPassword(usersDto.getPassword(),user.getPassword())){
            return TokenFactory.generateToken(user);
        }

        return "Wrong password";
    }
}
