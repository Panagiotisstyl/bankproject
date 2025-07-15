package com.root.bankproject.controllers;

import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserApi {

    @GetMapping("/users")
    List<UserResponseDto> findAll();

    @GetMapping("/users/{userId}")
    UserResponseDto findById(@PathVariable int userId);

    @PostMapping("/users")
    UserResponseDto registerUser(@RequestBody UsersDto usersDto);

    @PostMapping("/users")
    String userLogin(@RequestBody UsersDto usersDto);
}
