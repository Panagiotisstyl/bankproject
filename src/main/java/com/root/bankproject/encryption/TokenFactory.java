package com.root.bankproject.encryption;


import com.root.bankproject.entities.User;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenFactory {

    private final UsersService usersService;

    public static String generateToken(User user){

        long timestamp = System.currentTimeMillis();
        try{
            return SimpleAES.encrypt(user.getId() + "|" + timestamp);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    public  boolean validateToken(String token, int accId){
        String decrypt;
        try{
            decrypt=SimpleAES.decrypt(token);
        }
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        String[] parts = decrypt.split("\\|");
        if(parts.length!=2)
            return false;
        int userId=Integer.parseInt(parts[0]);
        boolean userHasAccess=false;


        List<User> users=usersService.findByAccountId(accId);

        for(User user:users){
            if (user.getId() == userId) {
                userHasAccess = true;
                break;
            }
        }

        return (userHasAccess)&&(System.currentTimeMillis() - Long.parseLong(parts[1])<60*60*1000);


    }

    public boolean validateTokenForDeposit(String authorizationHeader  ) {
        String decrypt;
        try{decrypt=SimpleAES.decrypt(authorizationHeader);}
        catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        String [] parts = decrypt.split("\\|");
        int userId=Integer.parseInt(parts[0]);

        User userX=usersService.findById(userId);
        return userX != null;
    }
}
