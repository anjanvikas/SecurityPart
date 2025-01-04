package com.SecurityPart.SecurityPart.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsernameOrEmailNotFoundException extends UsernameNotFoundException{

    public UsernameOrEmailNotFoundException(String msg) {
		super(msg);
	}
    
}
