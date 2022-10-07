package com.computer.server.entity.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_COMDATA")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String comType;
    private String imgUrl;
    @Column(length = 2000)
    private String spec;
    private String price;
    private String originalUrl;
    private String searchTag;
}
