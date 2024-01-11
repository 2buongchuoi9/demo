package com.den.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.den.entity._school;
import com.den.repository.SchoolRepo;

@Service
public class SchoollService {
  @Autowired
  private SchoolRepo schoolRepo;

  public List<_school> getAll() {
    return schoolRepo.findAll();
  }

}
