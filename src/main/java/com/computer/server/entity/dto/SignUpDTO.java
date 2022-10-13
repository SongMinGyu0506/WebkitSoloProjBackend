package com.computer.server.entity.dto;

import com.computer.server.entity.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpDTO {
    private int id;
    private String email;
    private String name;
    private String password;

    public SignUpDTO(final UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
    }

    public static UserEntity toUserEntity(final SignUpDTO signUpDTO) {
        UserEntity user = UserEntity.builder()
                .computers(null)
                .userDate(new Date())
                .isSecession(false)
                .isAdmin(false)
                .password(signUpDTO.getPassword())
                .name(signUpDTO.getName())
                .email(signUpDTO.getEmail())
                .build();
        return user;
    }
}
