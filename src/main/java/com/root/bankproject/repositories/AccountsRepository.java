package com.root.bankproject.repositories;

import com.root.bankproject.entities.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account,Integer> {
    @EntityGraph(attributePaths = {"users"})
    Optional<Account> findById(Integer id);
}
