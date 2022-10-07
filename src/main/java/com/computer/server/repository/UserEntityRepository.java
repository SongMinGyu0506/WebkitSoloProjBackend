package com.computer.server.repository;

import com.computer.server.entity.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,String> {
    boolean existsByEmail(String email);
    UserEntity findById(int id);

    UserEntity findByEmailAndPassword(String email, String password);

}
