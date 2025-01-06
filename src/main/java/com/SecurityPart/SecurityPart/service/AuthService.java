package com.SecurityPart.SecurityPart.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.SecurityPart.SecurityPart.exceptions.RoleNotFoundException;
import com.SecurityPart.SecurityPart.model.ERole;
import com.SecurityPart.SecurityPart.model.Role;
import com.SecurityPart.SecurityPart.model.User;
import com.SecurityPart.SecurityPart.repository.RoleRepo;
import com.SecurityPart.SecurityPart.repository.UserRepo;
import com.SecurityPart.SecurityPart.security.jwt.JwtUtils;



@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder encoder;

    public boolean userExistsByUsername(String userName) {

        return userRepo.existsByUserName(userName);
    }

    public boolean userExistsByEmail(String email) {

        return userRepo.existsByEmail(email);

    }

    public User registerUser(String userName, String email, String passWord, Set<String> strRoles) throws Exception{
        System.out.println(userName + " " + email + " " + passWord);
        Set<Role> roles = new HashSet<>();
        try{
            User user = new User(userName,encoder.encode(passWord),email);
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
            
            user.setRoles(roles);
            userRepo.save(user);
            return user;
        } catch(RoleNotFoundException e){
            throw new RuntimeException(e.getMessage(), e);
        } catch(Exception e){
            throw new RuntimeException("Error: User registration failed. Please try again later.", e);
        }

    }

    public String loginUser(String userNameorEmail, String passWord) throws Exception{

         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userNameorEmail, passWord));
         
         SecurityContextHolder.getContext().setAuthentication(authentication);

         UserDetailsImple userDetails = (UserDetailsImple)authentication.getPrincipal();

         ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

         
         
         return jwtCookie.toString();

    }

    public String logoutUser() {
        return jwtUtils.getCleanJwtCookie().toString();
    }
    
}
