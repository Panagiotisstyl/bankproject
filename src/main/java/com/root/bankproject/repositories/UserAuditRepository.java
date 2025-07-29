package com.root.bankproject.repositories;

import com.root.bankproject.entities.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuditRepository extends JpaRepository<UserAudit,Integer> {
}
