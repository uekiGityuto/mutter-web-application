package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
	@Query("SELECT x FROM User x WHERE x.name = :name")
	public Optional<User> findByName(String name);
	
}
