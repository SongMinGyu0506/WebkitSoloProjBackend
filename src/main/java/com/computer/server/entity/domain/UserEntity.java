package com.computer.server.entity.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_USER")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date userDate;

    @NotNull
    private Boolean isSecession;
    @NotNull
    private Boolean isAdmin;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "T_COMPAREDATA",
            joinColumns = @JoinColumn(name="id"),
            inverseJoinColumns = @JoinColumn(name="computerId")
    )
    private List<Computer> computers;
}
