package com.example.dayonetest.model;

public class StudentScoreFixture {

  public static StudentScore passed() {
    return StudentScore.builder()
        .studentName("defaultName")
        .exam("defaultExam")
        .korScore(90)
        .englishScore(80)
        .mathScore(90)
        .build();
  }

  public static StudentScore failed() {
    return StudentScore.builder()
        .studentName("defaultName")
        .exam("defaultExam")
        .korScore(20)
        .englishScore(30)
        .mathScore(50)
        .build();
  }
}
