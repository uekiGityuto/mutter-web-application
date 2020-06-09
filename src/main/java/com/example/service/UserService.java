package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder passwordEncoder;
	
	public User create(User user) {
		user.setPass(passwordEncoder.encode(user.getPass()));
		return userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
	
}
