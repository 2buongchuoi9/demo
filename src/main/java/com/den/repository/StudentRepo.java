package com.den.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.den.entity._student;

public interface StudentRepo extends JpaRepository<_student, Long> {

}
