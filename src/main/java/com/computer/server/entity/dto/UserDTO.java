package com.computer.server.entity.dto;
import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String token;
    private String email;
    private String name;
    private String password;
    private int id;
    private List<Computer> computers;
    private Date userDate;
    private Boolean isSecession;
    private Boolean isAdmin;

    public UserDTO(final UserEntity userEntity) {
        this.email = userEntity.getEmail();
        this.name = userEntity.getName();
        this.id = userEntity.getId();
        this.password = userEntity.getPassword();
        this.computers = userEntity.getComputers();
        this.userDate = userEntity.getUserDate();
        this.isAdmin = userEntity.getIsAdmin();
        this.isSecession = userEntity.getIsSecession();
    }
    public static UserEntity toUserEntity(final UserDTO dto) {
        return UserEntity.builder()
                .userDate(dto.getUserDate())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .id(dto.getId())
                .name(dto.getName())
                .computers(dto.getComputers())
                .isAdmin(dto.getIsAdmin())
                .isSecession(dto.getIsSecession())
                .build();
    }
}
