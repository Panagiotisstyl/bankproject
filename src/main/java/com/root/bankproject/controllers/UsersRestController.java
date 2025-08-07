package com.root.bankproject.controllers;


import com.root.bankproject.ExceptionHandler.RequestsHandler;
import com.root.bankproject.ExceptionHandler.Response;
import com.root.bankproject.command.UserCommand;
import com.root.bankproject.dtos.UserLoginDto;
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
    private final RequestsHandler requestsHandler;

    public Response<List<UserResponseDto>> findAll(){
        return requestsHandler.okReq(userCommand.findAll());
    }

    public Response<UserResponseDto> findById(@PathVariable int userId){
        return requestsHandler.okReq(userCommand.findById(userId));
    }

    public Response<UserResponseDto> registerUser(@RequestBody UsersDto usersDto){
        return requestsHandler.okReq(userCommand.registerUser(usersDto));
    }

    public Response<String> userLogin(@RequestBody UserLoginDto userLoginDto){
        return requestsHandler.okReq(userCommand.userLogin(userLoginDto));
    }

}
