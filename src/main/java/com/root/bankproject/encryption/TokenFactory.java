package com.root.bankproject.encryption;


import com.root.bankproject.entities.User;
import com.root.bankproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;

import java.util.Calendar;
import java.util.UUID;

@RequiredArgsConstructor
public class TokenFactory {

    public static String generateToken(User user){
        Calendar date=Calendar.getInstance();
        return UUID.randomUUID().toString().toUpperCase()
                + "|" + user.getId() + "|"
                + date.getTimeInMillis();

    }
    
}
