package com.den.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.den.entity._school;

import java.util.Optional;

@Repository
public interface SchoolRepo extends JpaRepository<_school, Long> {
  Optional<_school> findById(Long id);
}
