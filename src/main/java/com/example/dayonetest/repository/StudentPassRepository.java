package com.example.dayonetest.repository;

import com.example.dayonetest.model.StudentPass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentPassRepository extends JpaRepository<StudentPass, Long> {
}
