package com.den.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.text.SimpleDateFormat;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.den.entity._clazz;
import com.den.entity._student;
import com.den.exceptions.UnKnowError;
import com.den.repository.ClazzRepo;
import com.den.repository.StudentRepo;
import com.den.utils._enum;
import com.den.utils._enum.StatusStudentEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Service
public class ExcelUploadService {

  private final int ID = 0, NAME = 1, PHONE = 2, ADRRESS = 3, CLAZZ = 4, STATUS = 5, BIRTHDAY = 6, EMAIL = 7, IMAGE = 8;
  private final String[] HEADERs = { "Id", "Status", "Error" };

  @Autowired
  private ClazzRepo clazzRepo;
  @Autowired
  private StudentRepo studentRepo;
  @Autowired
  private SimpleDateFormat simpleDateFormat;

  public boolean isValidExcelFile(MultipartFile file) {
    return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
  }

  public List<DtoResultExcel> getResultDataFromExcel(InputStream inputStream) {
    List<DtoResultExcel> result = new ArrayList<>();
    // System.out.println("doooooo");
    try {
      XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
      XSSFSheet sheet = workbook.getSheet("Sheet1");
      int rowIndex = 0;
      for (Row row : sheet) {
        if (rowIndex == 0) {
          rowIndex++;
          continue;
        }
        boolean isUpdate = false;
        String mes = "";

        // 1 check id
        Long id = Double.valueOf(row.getCell(ID).getNumericCellValue()).longValue();
        Optional<_student> ostd = studentRepo.findById(id);
        isUpdate = ostd.isPresent();

        // 5 check clazz
        Long clazzId = Double.valueOf(row.getCell(CLAZZ).getNumericCellValue()).longValue();
        Optional<_clazz> ocla = clazzRepo.findById(clazzId);
        if (!ocla.isPresent()) {
          result.add(new DtoResultExcel(id, DtoResultExcel.FAIL, "class id not found"));
          continue;
        }

        // 2 name
        String name = row.getCell(NAME).getStringCellValue();

        // 3 phone
        String phone = row.getCell(PHONE).getNumericCellValue() + "";

        // 4 adrress
        String adrress = row.getCell(ADRRESS).getStringCellValue();

        // 6 check status
        String statusStr = row.getCell(STATUS).getStringCellValue();
        StatusStudentEnum statusStudentEnum = _enum.getEnumFromString(statusStr);
        int status = statusStudentEnum.value;

        // 7 birthday
        String birthdaySTR = row.getCell(BIRTHDAY).getStringCellValue();
        Date birthday = null;

        // 8 image
        String image = row.getCell(IMAGE).getStringCellValue();

        try {
          birthday = simpleDateFormat.parse(birthdaySTR);
        } catch (Exception e) {
          result.add(new DtoResultExcel(id, DtoResultExcel.FAIL, "birthday format error"));
          continue;
        }

        _student std = null;

        if (isUpdate) {
          std = ostd.get();
          std.setName(name);
          std.setPhone(phone);
          std.setAdrress(adrress);
          std.setStatus(status);
          std.setImage(image);
          std.setBirthday(birthday);

        } else {
          std = _student.builder()
              .id(id)
              .name(name)
              .phone(phone)
              .adrress(adrress)
              .status(status)
              .image(image)
              .birthday(birthday)
              .build();
        }
        studentRepo.save(std);
        result.add(isUpdate ? new DtoResultExcel(id, DtoResultExcel.UPDATE, "")
            : new DtoResultExcel(id, DtoResultExcel.INSERT, ""));

      }
    } catch (IOException e) {
      e.getStackTrace();
      throw new UnKnowError("read excel error");
    }
    return result;
  }

  public InputStreamResource writeExcel(List<DtoResultExcel> list) {
    Workbook workbook = new XSSFWorkbook();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Sheet sheet = workbook.createSheet("result");

    // Header
    Row headerRow = sheet.createRow(0);

    for (int col = 0; col < HEADERs.length; col++) {
      Cell cell = headerRow.createCell(col);
      cell.setCellValue(HEADERs[col]);
    }

    int rowIdx = 1;
    for (DtoResultExcel result : list) {
      Row row = sheet.createRow(rowIdx++);

      row.createCell(0).setCellValue(result.getId());
      row.createCell(1).setCellValue(result.getStatus());
      row.createCell(2).setCellValue(result.getError());
    }

    try {
      workbook.write(out);
    } catch (IOException e) {
      e.printStackTrace();
      throw new UnKnowError("fail to import data to Excel file: " + e.getMessage());
    }
    ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
    return new InputStreamResource(inputStream);
  }

  /**
   * DtoResultExcel
   */
  @Data
  @AllArgsConstructor
  public class DtoResultExcel {
    public static final String FAIL = "fail", UPDATE = "update", INSERT = "insert";
    private Long id;
    private String status; // fail, update, insert
    private String error;
  }
}
