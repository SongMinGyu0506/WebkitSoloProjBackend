package com.computer.server.service;

import com.computer.server.entity.domain.Computer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ComputerServiceTest {
    @Autowired
    ComputerService computerService;

    @Test
    public void allsearch() {
        List<Computer> computers = computerService.readAll();

        for(Computer computer : computers) {
            System.out.println(computer.getName());
        }
    }
}