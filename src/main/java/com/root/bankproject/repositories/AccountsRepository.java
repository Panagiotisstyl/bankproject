package com.root.bankproject.repositories;

import com.root.bankproject.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Account,Integer> {
}
