package com.algorithm.repository;

import com.algorithm.entity.Problem;
import com.algorithm.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findAllByProblemId(Long problemId);
}
