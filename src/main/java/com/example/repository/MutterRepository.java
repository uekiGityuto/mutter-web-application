package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.Mutter;

public interface MutterRepository extends JpaRepository<Mutter, Integer> {

}
