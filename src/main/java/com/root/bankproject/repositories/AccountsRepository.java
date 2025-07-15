package com.root.bankproject.repositories;

import com.root.bankproject.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account,Integer> {
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.users WHERE a.id = :id")
    Optional<Account> findByIdWithUsers(@Param("id") Integer id);
}
