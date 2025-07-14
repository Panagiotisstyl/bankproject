package com.root.bankproject.controllers;


import com.root.bankproject.converters.UsersConverter;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.validations.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class UsersRestController {


    private final UsersConverter usersConverter;
    private final UserValidation userValidation;

    @GetMapping("/users")
    public List<UserResponseDto> findAll(){
        return usersConverter.findAllConvert();
    }

    @GetMapping("/users/{userId}")
    public UserResponseDto findById(@PathVariable int userId){
        return usersConverter.findByIdConvert(userId);
    }

    @PostMapping("/users")
    public UserResponseDto registerUser(@RequestBody UsersDto usersDto){
        return usersConverter.registerUserConvert(usersDto);
    }

    @PostMapping("/users/login")
    public String userLogin(@RequestBody UsersDto usersDto){
        return userValidation.validateUser(usersDto);
    }

}
