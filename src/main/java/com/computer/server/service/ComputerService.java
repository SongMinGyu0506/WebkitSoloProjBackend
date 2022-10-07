package com.computer.server.service;

import com.computer.server.entity.domain.Computer;
import com.computer.server.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputerService {
    @Autowired
    private ComputerRepository computerRepository;

    public List<Computer> readAll() {
        return computerRepository.findAll();
    }
    public List<Computer> searchComputer(String type, String keyword) {
        return computerRepository.findByNameContainingAndComType(keyword,type);
    }

    public Computer inspectSearchComputer(int id) {
        return computerRepository.findById(id);
    }
}
