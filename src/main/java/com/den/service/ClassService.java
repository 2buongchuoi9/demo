package com.den.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.den.entity._clazz;
import com.den.repository.ClazzRepo;

@Service
public class ClassService {
  @Autowired
  private ClazzRepo classRepo;

  public List<_clazz> getAll() {
    return classRepo.findAll();
  }
}
