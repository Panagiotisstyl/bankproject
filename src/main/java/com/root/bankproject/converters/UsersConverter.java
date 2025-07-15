package com.root.bankproject.converters;

import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.entities.User;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsersConverter {

    public static User toEntity(UsersDto usersDto){
        return toEntity(usersDto,null);
    }

    public static User toEntity(UsersDto usersDto, @Nullable User existing){

        var builder = User.builder();

        if(existing != null){
            builder.id(existing.getId());
        }

        return builder
                .username(usersDto.getUsername())
                .password(BcryptHashing.hashPassword(usersDto.getPassword()))
                .email(usersDto.getEmail())
                .build();

    }

    public static UserResponseDto toResponseDto(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static List<UserResponseDto> toDtoList(List<User> users){
        return users.stream().map(UsersConverter::toResponseDto).collect(Collectors.toList());
    }

}
