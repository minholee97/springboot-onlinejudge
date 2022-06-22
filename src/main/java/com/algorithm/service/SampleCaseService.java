package com.algorithm.service;

import com.algorithm.dto.SampleCaseDto;
import com.algorithm.entity.Problem;
import com.algorithm.entity.SampleCase;
import com.algorithm.repository.ProblemRepository;
import com.algorithm.repository.SampleCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SampleCaseService {
    private final SampleCaseRepository sampleCaseRepository;
    private final ProblemRepository problemRepository;

    public void setSampleCase(SampleCaseDto sampleCaseDto) {
        Optional<Problem> optionalProblem = problemRepository.findById(sampleCaseDto.getProblemId());
        if (!optionalProblem.isPresent())
            return;
        Problem problem = optionalProblem.get();
        SampleCase sampleCase = new SampleCase(sampleCaseDto.getSampleInput(), sampleCaseDto.getSampleOutput(), problem);
        sampleCaseRepository.save(sampleCase);
    }
}
