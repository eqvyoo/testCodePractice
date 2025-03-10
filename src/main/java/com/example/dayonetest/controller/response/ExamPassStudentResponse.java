package com.example.dayonetest.controller.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ExamPassStudentResponse {
  private final String studentName;
  private final Double avgScore;
}
