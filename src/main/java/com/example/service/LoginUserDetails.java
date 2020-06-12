package com.example.service;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.example.domain.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class LoginUserDetails extends org.springframework.security.core.userdetails.User{
	private static final long serialVersionUID = 1L;
	private final User user;
	
	public LoginUserDetails(User user, Collection<GrantedAuthority> authorities) {
		super(user.getName(), user.getPass(), authorities);
		this.user = user;
	}
	
}
