package com.root.bankproject.controllers;

import com.root.bankproject.ExceptionHandler.Response;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/users")
public interface UserApi {

    @GetMapping
    Response<List<UserResponseDto>> findAll();

    @GetMapping("/{userId}")
    Response<UserResponseDto> findById(@PathVariable int userId);

    @PostMapping("/register")
    Response<UserResponseDto> registerUser(@RequestBody UsersDto usersDto);

    @PostMapping("/login")
    Response<String> userLogin(@RequestBody UsersDto usersDto);

}
