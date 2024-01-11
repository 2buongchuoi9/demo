package com.den.entity;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UuidGenerator.Style;

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
import lombok.Builder.Default;

@Table(name = "clazz")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class _clazz {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "code")
  private String code;
  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "school_id", nullable = false)
  private _school school;
  @Column(name = "status", columnDefinition = "boolean default true")
  @Default()
  private Boolean status = true;
}
