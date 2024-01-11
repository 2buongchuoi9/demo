package com.den.entity;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UuidGenerator.Style;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "school")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class _school {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "adrress", nullable = false)
  private String adrress;
  @Column(name = "email", nullable = false)
  private String email;
  @Column(name = "logo", nullable = false)
  private String logo;
}
