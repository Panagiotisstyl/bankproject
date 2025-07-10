package com.root.bankproject.services;

import com.root.bankproject.entities.Users;
import com.root.bankproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    public List<Users> findALl(){
        return usersRepository.findAll();
    }

    public Users findById(Integer id){
        return usersRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    public Users save(Users user){
        return usersRepository.save(user);
    }

    public boolean delete(int id){
        if(!usersRepository.existsById(id))
            return false;
        usersRepository.deleteById(id);
        return true;

    }

    public Users findByEmail(String email){
        Users user=usersRepository.findByEmail(email);
        if(user!=null)
            return user;
        throw new RuntimeException("User not found");
    }

}
