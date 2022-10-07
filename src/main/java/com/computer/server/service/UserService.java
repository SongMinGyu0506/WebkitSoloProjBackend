package com.computer.server.service;

import com.computer.server.entity.domain.UserEntity;
import com.computer.server.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserEntityRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)) {
            log.warn("Email already exist {}",email);
            throw new RuntimeException("Email already exist");
        }
        return userRepository.save(userEntity);
    }
    public UserEntity getByCredentials(final String email, final String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }
    public UserEntity getById(int id) {
        try {
            return userRepository.findById(id);
        } catch (NoSuchElementException NSE) {
            log.error(NSE.getMessage());
        }
        return null;
    }
    public UserEntity update(final UserEntity userEntity) {
        if(userRepository.existsById(Integer.toString(userEntity.getId()))) {
            userRepository.save(userEntity);
        } else {
            throw new RuntimeException("Unknown id");
        }
        return userRepository.findById(Integer.toString(userEntity.getId())).get();
    }

}
