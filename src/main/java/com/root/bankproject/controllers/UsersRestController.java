package com.root.bankproject.controllers;


import com.root.bankproject.converters.UsersConverter;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.entities.Users;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class UsersRestController {


    private final UsersService usersService;

    @GetMapping("/users")
    public List<UserResponseDto> findAll(){
        return UsersConverter.toDtoList(usersService.findALl());
    }

    @GetMapping("/users/{userId}")
    public UserResponseDto findById(@PathVariable int userId){
        return UsersConverter.toResponseDto(usersService.findById(userId));
    }

    @PostMapping("/users")
    public UserResponseDto registerUser(@RequestBody UsersDto usersDto){
        return UsersConverter.toResponseDto(usersService.save(UsersConverter.toEntity(usersDto)));
    }

    @PostMapping("/users")
    public String userLogin(@RequestBody UsersDto usersDto){
        Users user=usersService.findByEmail(usersDto.getEmail());
        if(BcryptHashing.verifyPassword(usersDto.getPassword(),user.getPassword())){
            return ""+user.getId();
        }
        return "Wrong Password";
    }

}
