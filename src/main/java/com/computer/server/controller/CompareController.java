package com.computer.server.controller;

import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.domain.UserEntity;
import com.computer.server.entity.dto.ComputerDTO;
import com.computer.server.entity.dto.ComputerRecommendDTO;
import com.computer.server.entity.dto.ResponseDTO;
import com.computer.server.service.CompareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequestMapping("compare")
public class CompareController {
    private CompareService compareService;

    @Autowired
    public CompareController(CompareService compareService) {
        this.compareService = compareService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCompareData(@AuthenticationPrincipal int id, @RequestBody ComputerDTO computer) {
        try {
            Computer computerEntity = ComputerDTO.toComputer(computer);
            UserEntity user = compareService.save(computerEntity, id);
            if (user == null) {
                ResponseDTO response = ResponseDTO.builder().error("same").build();
                return ResponseEntity.badRequest().body(response);
            }
            List<UserEntity> responseData = new ArrayList<>();
            responseData.add(user);
            ResponseDTO response = ResponseDTO.<UserEntity>builder().data(responseData).build();
            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/read")
    public ResponseEntity<?> readUserSaveData(@AuthenticationPrincipal int id) {
        try {
            List<Computer> userCompareData = compareService.read(id);
            ResponseDTO response = ResponseDTO.<Computer>builder().data(userCompareData).build();
            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/recommand-read")
    public ResponseEntity<?> readRecommendData(@AuthenticationPrincipal int id, @RequestBody ComputerDTO computerDTO) {
        try{
            List<ComputerRecommendDTO> readRecommendList = compareService.readRecommend(computerDTO.getSearchTag());
            ResponseDTO response = ResponseDTO.<ComputerRecommendDTO>builder().data(readRecommendList).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
            }
        }
    }
