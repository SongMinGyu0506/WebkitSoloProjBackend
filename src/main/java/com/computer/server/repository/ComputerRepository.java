package com.computer.server.repository;

import com.computer.server.entity.domain.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer,String> {
    List<Computer> findByNameContainingAndComType(String keyword, String type);
    Computer findById(int id);
    List<Computer> findBySearchTag(String keyword);
}
