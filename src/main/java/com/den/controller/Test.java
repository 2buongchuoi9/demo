package com.den.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.den.entity._clazz;
import com.den.entity._school;
import com.den.entity._student;
import com.den.models.StudentReq;
import com.den.repository.ClazzRepo;
import com.den.repository.SchoolRepo;
import com.den.repository.StudentRepo;
import com.den.service.CloudinaryService;
import com.den.service.StudentService;
import com.den.utils._enum.StatusStudentEnum;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class Test {

  final SchoolRepo schoolRepo;
  final ClazzRepo classRepo;
  final StudentRepo studentRepo;
  final StudentService studentService;
  final CloudinaryService cloudinaryService;

  @GetMapping("/test")
  public String test() {
    return "oke";
  }

  @GetMapping("/add")
  public ResponseEntity<?> add() {

    schoolRepo.save(
        _school.builder()
            .name("FPT")
            .adrress("HCM")
            .email("fpt@edu.vn")
            .logo("_")
            .build());

    _school s = schoolRepo.findAll().get(0);

    classRepo.save(
        _clazz.builder()
            .code("SD18203")
            .name("PTPM")
            .school(s)
            .build()

    );

    _clazz c = classRepo.findAll().get(0);

    studentRepo.save(
        _student.builder()
            .name("Dũng")
            .class_(c)
            .adrress("HCM")
            .birthday(new Date(1998, 01, 28))
            .email("dungnq27142@fpt.edu.vn")
            .phone("0936631402")
            .status(StatusStudentEnum.ACTIVE.value)
            .build());

    return ResponseEntity.ok().body(studentRepo.findAll());
  }

  @PostMapping("/up")
  public ResponseEntity<?> uploadImage(@ModelAttribute StudentReq studentReq) {
    return ResponseEntity.ok().body(studentService.add(studentReq));
  }

}
