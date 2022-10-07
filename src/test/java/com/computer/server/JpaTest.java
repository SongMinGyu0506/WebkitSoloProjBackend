package com.computer.server;

import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.domain.UserEntity;
import com.computer.server.repository.ComputerRepository;
import com.computer.server.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataJpaTest
public class JpaTest {
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    ComputerRepository computerRepository;

    @Test
    public void joinTest() {
        Computer computer = Computer.builder()
                .comType("CPU")
                .imgUrl("#")
                .originalUrl("#")
                .name("test")
                .spec("test")
                .price("000000")
                .build();
        List<Computer> computers = new ArrayList<>();
        computers.add(computer);
        UserEntity temp = UserEntity.builder()
                .password("#")
                .userDate(new Date())
                .isAdmin(false)
                .isSecession(false)
                .email("#")
                .computers(computers)
                .build();
        userEntityRepository.save(temp);

        List<UserEntity> list = userEntityRepository.findAll();
        UserEntity user = list.get(0);
        System.out.println(user.getEmail());
    }
}
