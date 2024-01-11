package com.den.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.den.entity._clazz;
import com.den.entity._school;
import com.den.entity._student;
import com.den.exceptions.NotFoundError;
import com.den.models.StudentReq;
import com.den.repository.ClazzRepo;
import com.den.repository.SchoolRepo;
import com.den.repository.StudentRepo;

@Service
public class StudentService {
  @Autowired
  private StudentRepo studentRepo;
  @Autowired
  private ClazzRepo classRepo;
  @Autowired
  private SchoolRepo schoolRepo;
  @Autowired
  private SimpleDateFormat simpleDateFormat;
  @Autowired
  private CloudinaryService cloudinaryService;

  public List<_student> getAll() {
    return studentRepo.findAll();
  }

  public _student add(StudentReq stdReq) {

    // check school
    _school foundSchool = schoolRepo.findById(stdReq.getSchoolId())
        .orElseThrow(() -> new NotFoundError("school id not found"));

    // check class
    _clazz foundClass = null;
    if (stdReq.getClassId() != null) {
      foundClass = classRepo.findById(stdReq.getClassId())
          .orElseThrow(() -> new NotFoundError("class id not found"));
    }

    // check date
    Date birthday = null;
    try {
      birthday = simpleDateFormat.parse(stdReq.getBirthday());
    } catch (ParseException e) {
      throw new NotFoundError("birthday is not format (dd-MM-yyyy)");
    }

    // get img

    String link = cloudinaryService.upload(stdReq.getImg());

    _student student = studentRepo.save(
        _student.builder()
            .adrress(stdReq.getAdrress())
            .birthday(birthday)
            .email(stdReq.getEmail())
            .image(link)
            .adrress(stdReq.getAdrress())
            .class_(foundClass)
            .name(stdReq.getName())
            .build());

    return student;

  }
}
