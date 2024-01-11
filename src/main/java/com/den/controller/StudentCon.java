package com.den.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.den.exceptions.UnKnowError;
import com.den.service.ExcelUploadService;
import com.den.service.StudentService;
import com.den.service.ExcelUploadService.DtoResultExcel;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/student")
public class StudentCon {
  @Autowired
  private StudentService studentService;
  @Autowired
  private ExcelUploadService excelUploadService;

  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(studentService.getAll());
  }

  @PostMapping("/upload-excel")
  public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile multipartFile) {

    List<DtoResultExcel> list = null;
    try {
      list = excelUploadService.getResultDataFromExcel(multipartFile.getInputStream());
    } catch (IOException e) {
      e.printStackTrace();
      throw new UnKnowError("excel error");
    }
    InputStreamResource file = excelUploadService.writeExcel(list);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "resoult.xlsx")
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
        .body(file);
  }

}
