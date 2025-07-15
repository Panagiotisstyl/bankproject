package com.root.bankproject.encryption;


import com.root.bankproject.entities.Users;
import com.root.bankproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.UUID;

@RequiredArgsConstructor
public class TokenFactory {

    private final UsersRepository usersRepository;

    public static String generateToken(Users user){
        Calendar date=Calendar.getInstance();
        return UUID.randomUUID().toString().toUpperCase()
                + "|" + user.getId() + "|"
                + date.getTimeInMillis();

    }

    //TODO: REMOVE
    public boolean validateToken(String token){

        String[] parts = token.split("\\|");

        return (System.currentTimeMillis() - Long.parseLong(parts[2])<  60*60*1000) && (usersRepository.existsById(Integer.parseInt(parts[1])));


    }
}
