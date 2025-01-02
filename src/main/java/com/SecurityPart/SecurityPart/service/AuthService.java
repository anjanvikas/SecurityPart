package com.SecurityPart.SecurityPart.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SecurityPart.SecurityPart.exceptions.RoleNotFoundException;
import com.SecurityPart.SecurityPart.model.ERole;
import com.SecurityPart.SecurityPart.model.Role;
import com.SecurityPart.SecurityPart.model.User;
import com.SecurityPart.SecurityPart.repository.RoleRepo;
import com.SecurityPart.SecurityPart.repository.UserRepo;



@Service
public class AuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    public boolean userExistsByUsername(String userName) {

        return userRepo.existsByUserName(userName);
    }

    public boolean userExistsByEmail(String email) {

        return userRepo.existsByEmail(email);

    }

    public User registerUser(String userName, String email, String passWord, Set<String> strRoles) {
        System.out.println(strRoles);
        Set<Role> roles = new HashSet<>();
        try{
            if (strRoles == null) {
                Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                roles.add(userRole);
                } else {
                    strRoles.forEach(role -> {
                    switch (role) {
                    case "admin":
                        Role adminRole = roleRepo.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);
            
                        break;
                    default:
                        Role userRole = roleRepo.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                    });
                }
            User user = new User(userName,passWord,email);
            
            user.setRoles(roles);
            userRepo.save(user);
            return user;
        } catch(RoleNotFoundException e){
            throw new RuntimeException(e.getMessage(), e);
        } catch(Exception e){
            throw new RuntimeException("Error: User registration failed. Please try again later.", e);
        }

    }
    
}
