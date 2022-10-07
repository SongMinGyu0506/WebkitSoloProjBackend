package com.computer.server.entity.dto;

import com.computer.server.entity.domain.AccountLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountLogDTO {
    private int id;
    private String loginType;
    private Date loginDate;
    private int loginId;
    private String loginEmail;

    public static AccountLog toAccountLog(AccountLogDTO accountLogDTO) {
        return AccountLog.builder()
                .loginEmail(accountLogDTO.getLoginEmail())
                .loginDate(accountLogDTO.getLoginDate())
                .loginId(accountLogDTO.getLoginId())
                .loginType(accountLogDTO.getLoginType())
                .build();
    }
}
