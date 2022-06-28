package com.algorithm.repository;

import com.algorithm.entity.SampleCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SampleCaseRepository extends JpaRepository<SampleCase, Long> {
    @Transactional
    void deleteAllByProblemId(Long problemId);
}
