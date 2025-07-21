package com.root.bankproject.services;

import com.root.bankproject.entities.User;
import com.root.bankproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public List<User> findALl(){
        return usersRepository.findAll();
    }

    public User findById(Integer id){
        return usersRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User save(User user){
        return usersRepository.save(user);
    }


    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> findByAccountId(int accountId) {
        return usersRepository.findAllByAccounts_Id(accountId);
    }
}
