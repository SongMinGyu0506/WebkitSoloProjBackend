package com.computer.server.service;

import com.computer.server.config.SchedulerService;
import com.computer.server.entity.dto.ComputerRecommendDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CompareServiceTest {
    @Autowired
    CompareService compareService;

    @Autowired
    SchedulerService schedulerService;

    @Test
    public void test() {
        List<ComputerRecommendDTO> list = compareService.readRecommend("ryzen5");
        for(ComputerRecommendDTO dto : list) {
            try {
                System.out.println(this + " : " + dto.toString());
//                System.out.println("TEST : "+dto.getComType());
//                System.out.println("TEST : "+dto.getName());
//                System.out.println("TEST : "+ dto.getPrice().get(0));
            } catch (Exception e) {
                System.out.println("TEST : []");
            }

        }
    }
}