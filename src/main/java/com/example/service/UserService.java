package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
	
	public void create(User user) throws DataAccessException {
		
		long userNum = userRepository.count();
		// 初回登録者は管理者にする
		if (userNum == 0) {
			user.setAdmin(true);
		} else {
			user.setAdmin(false);
		}
		user.setPass(passwordEncoder.encode(user.getPass()));
		user.setEnable(true);
		userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
	
}
