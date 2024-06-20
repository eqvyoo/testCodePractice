package com.example.dayonetest.service;

import com.example.dayonetest.MyCalculator;
import com.example.dayonetest.controller.response.ExamFailStudentResponse;
import com.example.dayonetest.controller.response.ExamPassStudentResponse;
import com.example.dayonetest.model.StudentFail;
import com.example.dayonetest.model.StudentPass;
import com.example.dayonetest.model.StudentScore;
import com.example.dayonetest.respository.StudentFailRepository;
import com.example.dayonetest.respository.StudentPassRepository;
import com.example.dayonetest.respository.StudentScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;

    public void saveScore(String studentName, String exam, Integer korScore, Integer englishScore, Integer mathScore) {
        StudentScore studentScore = StudentScore.builder()
                .studentName(studentName)
                .exam(exam)
                .korScore(korScore)
                .englishScore(englishScore)
                .mathScore(mathScore)
                .build();

        studentScoreRepository.save(studentScore);

        MyCalculator calculator = new MyCalculator(0.0);
        Double avgScore = calculator
                .add(korScore.doubleValue())
                .add(englishScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if (avgScore >= 60) {
            studentPassRepository.save(
                    StudentPass.builder()
                            .studentName(studentName)
                            .exam(exam)
                            .avgScore(avgScore)
                            .build()
            );
        } else {
            studentFailRepository.save(
                    StudentFail.builder()
                            .studentName(studentName)
                            .exam(exam)
                            .avgScore(avgScore)
                            .build()
            );
        }
    }

    public List<ExamPassStudentResponse> getPassStudentsList(String exam){
        List<StudentPass> studentPasses = studentPassRepository.findAll();

        return studentPasses.stream()
                .filter((pass) -> pass.getExam().equals(exam))
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
    }

    public List<ExamFailStudentResponse> getFailStudentsList(String exam){
        List<StudentFail> studentFails = studentFailRepository.findAll();

        return studentFails.stream()
                .filter((fail) -> fail.getExam().equals(exam))
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
    }

}

