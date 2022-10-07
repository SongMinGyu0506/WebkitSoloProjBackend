package com.computer.server.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "T_ACCOUNTLOG")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String loginType;
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date loginDate;
    private int loginId;
    private String loginEmail;
}
