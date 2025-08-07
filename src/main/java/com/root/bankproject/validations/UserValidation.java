package com.root.bankproject.validations;


import com.root.bankproject.dtos.UserLoginDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.encryption.TokenFactory;
import com.root.bankproject.entities.User;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Component
public class UserValidation {

    private final UsersService usersService;

    public String validateUser(@RequestBody UserLoginDto userLoginDto){
        User user=usersService.findByEmail(userLoginDto.getEmail());
        if(BcryptHashing.verifyPassword(userLoginDto.getPassword(),user.getPassword())){
            return TokenFactory.generateToken(user);
        }

        return "Wrong password";
    }
}
