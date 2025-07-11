package com.root.bankproject.converters;

import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.entities.Users;
import com.root.bankproject.services.UsersService;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UsersConverter {


    private final UsersService usersService;

    public static Users toEntity(UsersDto usersDto){
        return toEntity(usersDto,null);
    }

    public static Users toEntity(UsersDto usersDto, @Nullable Users existing){

        var builder = Users.builder();

        if(existing != null){
            builder.id(existing.getId());
        }

        return builder
                .username(usersDto.getUsername())
                .password(BcryptHashing.hashPassword(usersDto.getPassword()))
                .email(usersDto.getEmail())
                .build();

    }

    public static UserResponseDto toResponseDto(Users user){
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public static List<UserResponseDto> toDtoList(List<Users> users){
        return users.stream().map(UsersConverter::toResponseDto).collect(Collectors.toList());
    }

    public List<UserResponseDto> findAllConvert(){
        return toDtoList(usersService.findALl());
    }

    public UserResponseDto findByIdConvert(int userId){
        return toResponseDto(usersService.findById(userId));
    }

    public UserResponseDto registerUserConvert(UsersDto usersDto) {
        return toResponseDto(usersService.save(toEntity(usersDto)));
    }


}
