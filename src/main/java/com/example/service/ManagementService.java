package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.repository.UserRepository;

@Service
public class ManagementService {
	@Autowired UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void enable(Integer id) {
		User user = userRepository.findById(id).get();
		user.setEnable(true);
		userRepository.flush();
	}
	
	public void disable(Integer id) {
		User user = userRepository.findById(id).get();
		user.setEnable(false);
		userRepository.flush();
	}
	
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

}
