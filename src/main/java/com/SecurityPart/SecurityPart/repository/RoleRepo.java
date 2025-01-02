package com.SecurityPart.SecurityPart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.SecurityPart.SecurityPart.model.ERole;
import com.SecurityPart.SecurityPart.model.Role;

public interface RoleRepo extends JpaRepository<Role,Integer>{

    Optional<Role> findByName(ERole name);
}
