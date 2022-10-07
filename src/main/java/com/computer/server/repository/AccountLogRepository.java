package com.computer.server.repository;

import com.computer.server.entity.domain.AccountLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountLogRepository extends JpaRepository<AccountLog,String> {
}
