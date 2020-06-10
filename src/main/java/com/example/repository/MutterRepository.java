package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.Mutter;

public interface MutterRepository extends JpaRepository<Mutter, Integer> {
	@Query("SELECT x FROM Mutter x ORDER BY x.id DESC")
	Page<Mutter> findAllDescOrderById(Pageable pageable);
}
