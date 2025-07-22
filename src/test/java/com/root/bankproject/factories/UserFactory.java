package com.root.bankproject.factories;

import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.entities.User;

public class UserFactory {

    public static User createUser(String username, String email, String password) {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }

    public static UsersDto createUsersDto(String username, String email, String password) {
        return UsersDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
    }
}
