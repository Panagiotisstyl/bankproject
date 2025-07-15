package com.root.bankproject.controllers;


import com.root.bankproject.command.UserCommand;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersRestController implements UserApi{


    private final UserCommand userCommand;

    public List<UserResponseDto> findAll(){
        return userCommand.findAll();
    }

    public UserResponseDto findById(@PathVariable int userId){
        return userCommand.findById(userId);
    }

    public UserResponseDto registerUser(@RequestBody UsersDto usersDto){
        return userCommand.registerUser(usersDto);
    }

    @PostMapping("/users/login")
    public String userLogin(@RequestBody UsersDto usersDto){
        return userCommand.userLogin(usersDto);
    }

}
