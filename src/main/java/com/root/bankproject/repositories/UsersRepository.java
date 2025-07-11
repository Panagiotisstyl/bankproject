package com.root.bankproject.repositories;

import com.root.bankproject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByEmail(String email);
}
