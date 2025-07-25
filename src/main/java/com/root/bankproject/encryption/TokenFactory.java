package com.root.bankproject.encryption;


import com.root.bankproject.entities.User;
import com.root.bankproject.services.AccountsService;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TokenFactory {

    private final UsersService usersService;

    public static String generateToken(User user){

        long timestamp = System.currentTimeMillis();
        return UUID.randomUUID().toString().toUpperCase()
                + "|" + user.getId() + "|" + timestamp;
    }

    public  boolean validateToken(String token, int accId){

        String[] parts = token.split("\\|");
        if(parts.length!=3)
            return false;
        int userId=Integer.parseInt(parts[1]);
        boolean userHasAccess=false;


        List<User> users=usersService.findByAccountId(accId);

        for(User user:users){
            if (user.getId() == userId) {
                userHasAccess = true;
                break;
            }
        }

        return (userHasAccess)&&(System.currentTimeMillis() - Long.parseLong(parts[2])<60*60*1000);


    }

    public boolean validateTokenForDeposit(String authorizationHeader  ) {
        String [] parts = authorizationHeader.split("\\|");
        int userId=Integer.parseInt(parts[1]);

        User userX=usersService.findById(userId);
        return userX != null;
    }
}
