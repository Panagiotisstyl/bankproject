package com.root.bankproject.command;

import com.root.bankproject.converters.UsersConverter;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.services.UsersService;
import com.root.bankproject.validations.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class UserCommand {

    private final UsersService usersService;
    private final UserValidation userValidation;

    public List<UserResponseDto> findAll(){
        return UsersConverter.toDtoList(usersService.findALl());
    }

    public UserResponseDto findById(int userId){
        return UsersConverter.toResponseDto(usersService.findById(userId));
    }

    public UserResponseDto registerUser(UsersDto usersDto) {
        return UsersConverter.toResponseDto(usersService.save(UsersConverter.toEntity(usersDto)));
    }

    public String userLogin(UsersDto usersDto){
        return userValidation.validateUser(usersDto);
    }

}
