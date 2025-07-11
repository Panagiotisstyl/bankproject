package com.root.bankproject.repositories;

import com.root.bankproject.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts,Integer> {
}
