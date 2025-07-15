package com.root.bankproject.repositories;

import com.root.bankproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);
}
