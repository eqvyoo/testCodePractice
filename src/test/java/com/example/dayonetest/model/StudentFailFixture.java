package com.example.dayonetest.model;

import com.example.dayonetest.MyCalculator;

public class StudentFailFixture {
  public static StudentFail create(StudentScore studentScore) {

    var calculator = new MyCalculator();

    return StudentFail.builder()
        .studentName(studentScore.getStudentName())
        .exam(studentScore.getExam())
        .avgScore(
            calculator
                .add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult())
        .build();
  }

  public static StudentFail create(String studentName, String exam) {
    return StudentFail.builder().studentName(studentName).exam(exam).avgScore(50.0).build();
  }
}
