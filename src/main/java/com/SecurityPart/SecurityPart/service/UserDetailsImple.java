package com.SecurityPart.SecurityPart.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.SecurityPart.SecurityPart.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDetailsImple implements UserDetails{
    
    private Long Id;

    private String userName;

    private String passWord;

    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

        return new UserDetailsImple(
                            user.getId(), 
                            user.getUserName(), 
                            user.getEmail(),
                            user.getPassWord(), 
                            authorities);
  }

    @Override
    public String getPassword() {
        return this.userName;
    }

    @Override
    public String getUsername() {
        return this.passWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImple user = (UserDetailsImple) o;
        return Objects.equals(this.Id, user.Id);
    }
}
