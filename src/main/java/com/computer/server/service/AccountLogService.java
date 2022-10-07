package com.computer.server.service;

import com.computer.server.entity.domain.AccountLog;
import com.computer.server.entity.dto.AccountLogDTO;
import com.computer.server.repository.AccountLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountLogService {
    private AccountLogRepository accountLogRepository;

    @Autowired
    public AccountLogService(AccountLogRepository accountLogRepository) {
        this.accountLogRepository = accountLogRepository;
    }

    public AccountLog save(AccountLog accountLog) {
        return accountLogRepository.save(accountLog);
    }

    public List<AccountLog> read() {
        return accountLogRepository.findAll();
    }
}
