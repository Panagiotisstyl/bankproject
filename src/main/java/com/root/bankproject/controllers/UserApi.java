package com.root.bankproject.controllers;

import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/users")
public interface UserApi {

    @GetMapping
    List<UserResponseDto> findAll();

    @GetMapping("/{userId}")
    UserResponseDto findById(@PathVariable int userId);

    @PostMapping("/register")
    UserResponseDto registerUser(@RequestBody UsersDto usersDto);

    @PostMapping("/login")
    String userLogin(@RequestBody UsersDto usersDto);

}
