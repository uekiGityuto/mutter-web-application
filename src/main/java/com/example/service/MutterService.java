package com.example.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Mutter;
import com.example.domain.User;
import com.example.repository.MutterRepository;

@Service
@Transactional
public class MutterService {
	@Autowired MutterRepository mutterRepository;
	
	public Page<Mutter> findAll(Pageable pageable) {
		return mutterRepository.findAllDescOrderById(pageable);
	}
	
	public Mutter create(Mutter mutter, User user) {
		mutter.setTimestamp(new Date());
		mutter.setUser(user);
		return mutterRepository.save(mutter);
	}

}
