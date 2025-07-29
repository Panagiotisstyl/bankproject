package com.root.bankproject.repositories;


import com.root.bankproject.entities.AccountAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAuditRepository extends JpaRepository<AccountAudit,Integer> {
}
