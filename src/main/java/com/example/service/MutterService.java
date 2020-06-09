package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Mutter;
import com.example.domain.User;
import com.example.repository.MutterRepository;

@Service
@Transactional
public class MutterService {
	@Autowired MutterRepository mutterRepository;
	
	public List<Mutter> findAll() {
		return mutterRepository.findAll();
	}
	
	public Mutter create(Mutter mutter, User user) {
		mutter.setUser(user);
		return mutterRepository.save(mutter);
	}

/*	
	public Mutter create(Mutter mutter) {
		return mutterRepository.save(mutter);
	}
*/
}
