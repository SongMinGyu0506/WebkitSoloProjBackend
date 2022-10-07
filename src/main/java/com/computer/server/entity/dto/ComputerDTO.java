package com.computer.server.entity.dto;

import com.computer.server.entity.domain.Computer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComputerDTO {
    private int id;
    private String name;
    private String comType;
    private String imgUrl;
    private String spec;
    private String price;
    private String originalUrl;
    private String searchTag;

    public static Computer toComputer(ComputerDTO computerDTO) {
        return Computer.builder()
                .price(computerDTO.getPrice())
                .spec(computerDTO.getSpec())
                .id(computerDTO.getId())
                .name(computerDTO.getName())
                .originalUrl(computerDTO.getOriginalUrl())
                .imgUrl(computerDTO.getImgUrl())
                .comType(computerDTO.getComType())
                .searchTag(computerDTO.getSearchTag())
                .build();
    }
}
