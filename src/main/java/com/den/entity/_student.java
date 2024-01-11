package com.den.entity;

import java.util.Date;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UuidGenerator.Style;
import org.springframework.boot.context.properties.bind.DefaultValue;

import com.den.utils._enum.StatusStudentEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "student")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class _student {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", columnDefinition = "varchar(100)")
  private String name;

  @ManyToOne
  @JoinColumn(name = "class_id", nullable = true)
  private _clazz class_;

  @Column(name = "birthday")
  private Date birthday;

  @Column(name = "adrress", columnDefinition = "varchar(100)")
  private String adrress;

  @Column(name = "email", columnDefinition = "varchar(100)", nullable = true)
  private String email;

  @Column(name = "phone", columnDefinition = "varchar(13)", nullable = true)
  private String phone;

  @Column(name = "status", columnDefinition = "int default 0")
  private int status;

  @Column(name = "image", columnDefinition = "varchar(100)")
  private String image;
}
