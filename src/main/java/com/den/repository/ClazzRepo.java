package com.den.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.den.entity._clazz;
import java.util.List;
import java.util.Optional;

public interface ClazzRepo extends JpaRepository<_clazz, Long> {
  Optional<_clazz> findById(Long id);
}
