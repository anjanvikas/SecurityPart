package com.SecurityPart.SecurityPart.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SecurityPart.SecurityPart.exceptions.UsernameOrEmailNotFoundException;
import com.SecurityPart.SecurityPart.model.User;
import com.SecurityPart.SecurityPart.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImple implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Transactional
    private UserDetails loadUserByUsernameOrEmail(String usernameOrEmail) throws UsernameOrEmailNotFoundException{
        Optional<User> user = userRepo.findByUserName(usernameOrEmail);
        if(user.isEmpty()){
            user = userRepo.findByEmail(usernameOrEmail);
        }
        if(user.isEmpty()){
            throw new UsernameOrEmailNotFoundException("Not Found "+ usernameOrEmail);
        }
        System.out.println("User Found: " + user.get().getUserName());
        return UserDetailsImple.build(user.get());
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameOrEmailNotFoundException {
        return loadUserByUsernameOrEmail(usernameOrEmail);
    }

}
