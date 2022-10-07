package com.computer.server.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ComputerRecommendDTO {
    private int id;
    private String name;
    private String comType;
    private String imgUrl;
    private String spec;
    private List<Integer> price;
    private String originalUrl;
}
