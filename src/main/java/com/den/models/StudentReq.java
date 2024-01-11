package com.den.models;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentReq {
  private Long classId;
  private Long schoolId;
  private String name;
  private String birthday;
  private MultipartFile img;
  private String adrress;
  private String email;
  private String phone;
  private String status;
}
