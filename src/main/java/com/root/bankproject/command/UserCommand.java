package com.root.bankproject.command;

import com.root.bankproject.converters.UsersConverter;
import com.root.bankproject.dtos.UserLoginDto;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.services.UsersService;
import com.root.bankproject.validations.UserValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class UserCommand {

    private final UsersService usersService;
    private final UserValidation userValidation;
    private final KafkaTemplate<String, UserResponseDto> kafkaTemplateUserResponse;

    public List<UserResponseDto> findAll(){
        return UsersConverter.toDtoList(usersService.findALl());
    }

    public UserResponseDto findById(int userId){
        return UsersConverter.toResponseDto(usersService.findById(userId));
    }

    public UserResponseDto registerUser(UsersDto usersDto) {
        UserResponseDto userDto=UsersConverter.toResponseDto(usersService.save(UsersConverter.toEntity(usersDto)));
        kafkaTemplateUserResponse.send("user.created", userDto);
        return userDto;
    }

    public String userLogin(UserLoginDto userLoginDto){
        return userValidation.validateUser(userLoginDto);
    }

}
