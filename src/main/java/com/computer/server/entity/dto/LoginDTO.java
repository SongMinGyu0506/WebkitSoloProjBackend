package com.computer.server.entity.dto;

import com.computer.server.entity.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {
    private int id;
    private String email;
    private String password;
    private String token;
}
