package com.root.bankproject.repositories;

import com.root.bankproject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//TODO: change to optional
public interface UsersRepository extends JpaRepository<Users,Integer> {

    Optional<Users> findByEmail(String email);
}
