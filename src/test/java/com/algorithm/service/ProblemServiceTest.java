package com.algorithm.service;

import com.algorithm.dto.ProblemDto;
import com.algorithm.entity.Problem;
import com.algorithm.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProblemServiceTest {

    @Autowired
    ProblemRepository problemRepository;

}