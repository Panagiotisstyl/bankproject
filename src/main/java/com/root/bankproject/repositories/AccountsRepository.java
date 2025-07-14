package com.root.bankproject.repositories;

import com.root.bankproject.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Accounts,Integer> {
    @Query("SELECT a FROM Accounts a LEFT JOIN FETCH a.users WHERE a.id = :id")
    Optional<Accounts> findByIdWithUsers(@Param("id") Integer id);
}
