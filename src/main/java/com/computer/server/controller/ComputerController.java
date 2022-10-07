package com.computer.server.controller;

import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.dto.ResponseDTO;
import com.computer.server.entity.dto.SearchDTO;
import com.computer.server.service.ComputerService;
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
@RequestMapping("computer")
public class ComputerController {

    @Autowired
    ComputerService computerService;

    @GetMapping()
    public ResponseEntity<?> showAll(@AuthenticationPrincipal int id) {
        try {
            List<Computer> computers = computerService.readAll();
            ResponseDTO responseDTO = ResponseDTO.<Computer>builder().data(computers).build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.<Computer>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @PostMapping("/search")
    public ResponseEntity<?> searchComputer(@AuthenticationPrincipal int id, @RequestBody SearchDTO searchData) {
        try {
            List<Computer> computers = computerService.searchComputer(searchData.getType(),searchData.getName());
            ResponseDTO responseDTO = ResponseDTO.<Computer>builder().data(computers).build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.<Computer>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @GetMapping("/inspect")
    public ResponseEntity<?> inspectComputer(@AuthenticationPrincipal int id, @RequestParam int comId) {
        try {
            Computer computer = computerService.inspectSearchComputer(comId);
            List<Computer> responseComputer = new ArrayList<>();
            responseComputer.add(computer);
            ResponseDTO responseDTO = ResponseDTO.<Computer>builder().data(responseComputer).build();
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.<Computer>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
