package com.algorithm.repository;

import com.algorithm.dto.ProblemDto;
import com.algorithm.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Page<Problem> findAllBy(Pageable pageable);
}
