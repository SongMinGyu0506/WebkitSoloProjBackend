package com.computer.server.service;

import com.computer.server.entity.domain.Computer;
import com.computer.server.entity.domain.UserEntity;
import com.computer.server.entity.dto.ComputerRecommendDTO;
import com.computer.server.repository.ComputerRepository;
import com.computer.server.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Service
@Slf4j
public class CompareService {
    private UserEntityRepository userEntityRepository;
    private ComputerRepository computerRepository;

    @Autowired
    public CompareService(UserEntityRepository userEntityRepository, ComputerRepository computerRepository) {
        this.userEntityRepository = userEntityRepository;
        this.computerRepository = computerRepository;
    }

    public UserEntity save(Computer computer, int id) {
        UserEntity user = userEntityRepository.findById(id);
        List<Computer> userComputer = user.getComputers();
        Iterator<Computer> iterator = userComputer.iterator();
        while (iterator.hasNext()) {
            Computer com = iterator.next();
            if (com.getId() == id) {
                return null;
            }
        }
        userComputer.add(computer);
        user.setComputers(userComputer);
        return userEntityRepository.save(user);
    }
    public List<Computer> read(int id) {
        UserEntity user = userEntityRepository.findById(id);
        return user.getComputers();
    }

    public List<ComputerRecommendDTO> readRecommend(String searchTag) {
        try {
            List<ComputerRecommendDTO> result = new ArrayList<>();
            List<Computer> tempList = computerRepository.findBySearchTag(searchTag);

            List<Integer> tempPriceResult = new ArrayList<>();
            for(Computer computer : tempList) {
                String[] tempPrice = computer.getPrice().split(" ");
                for (String price : tempPrice) {
                    try {
                        tempPriceResult.add(NumberFormat.getNumberInstance(Locale.UK).parse(price).intValue());
                    } catch (ParseException pe) {
                        tempPriceResult.add(Integer.MAX_VALUE);
                    }
                }

                tempPriceResult.sort(Comparator.naturalOrder());

                ComputerRecommendDTO recommendDTO = getComputerRecommendDTO(tempPriceResult, computer);
                result.add(recommendDTO);
                tempPriceResult = new ArrayList<>();
            }

            objectSort(result);
            return result.subList(0,10);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private ComputerRecommendDTO getComputerRecommendDTO(List<Integer> tempPriceResult, Computer computer) {
        ComputerRecommendDTO recommendDTO = ComputerRecommendDTO.builder()
                .comType(computer.getComType())
                .id(computer.getId())
                .imgUrl(computer.getImgUrl())
                .name(computer.getName())
                .originalUrl(computer.getOriginalUrl())
                .price(tempPriceResult)
                .spec(computer.getSpec())
                .build();
        return recommendDTO;
    }

    private void objectSort(List<ComputerRecommendDTO> result) {
        Collections.sort(result, (c1, c2) -> {
            if (c1.getPrice().get(0) < c2.getPrice().get(0)) {
                return -1;
            } else if (c1.getPrice().get(0) > c2.getPrice().get(0)) {
                return 1;
            } else return 0;
        });
    }
}
