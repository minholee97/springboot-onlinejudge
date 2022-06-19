package com.algorithm.repository;

import com.algorithm.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByProblemIdOrderById(Long problemId);
}
