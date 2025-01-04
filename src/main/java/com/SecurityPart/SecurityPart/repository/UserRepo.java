package com.SecurityPart.SecurityPart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.SecurityPart.SecurityPart.model.User;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{
    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
