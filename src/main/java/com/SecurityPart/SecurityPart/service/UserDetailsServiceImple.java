package com.SecurityPart.SecurityPart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.SecurityPart.SecurityPart.model.User;
import com.SecurityPart.SecurityPart.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImple implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Not Found "+ username));
        return UserDetailsImple.build(user);
    }
    
}
