package com.algorithm.repository;

import com.algorithm.entity.SampleCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleCaseRepository extends JpaRepository<SampleCase, Long> {

}
