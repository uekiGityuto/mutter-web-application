package com.example.service;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.example.domain.User;

//org.springframework.security.core.userdetails.Userで
//ボイラープレートコードを定義しているのでlombokは使わない(変にOverrideすると挙動がおかしくなる)
public class LoginUserDetails extends org.springframework.security.core.userdetails.User{
	private static final long serialVersionUID = 1L;
	private final User user;
	
	public LoginUserDetails(User user, Collection<GrantedAuthority> authorities) {
		super(user.getName(), user.getPass(), user.isEnable(), true, true, true, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}	
	
}
