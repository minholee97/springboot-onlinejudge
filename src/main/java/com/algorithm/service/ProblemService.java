package com.algorithm.service;

import com.algorithm.dto.ProblemDetailDto;
import com.algorithm.dto.ProblemDto;
import com.algorithm.dto.SampleCaseDto;
import com.algorithm.entity.Problem;
import com.algorithm.entity.SampleCase;
import com.algorithm.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<ProblemDto> getProblemDtoList() {
        List<Problem> problemList = problemRepository.findAll();
        List<ProblemDto> problemDtoList = new ArrayList<>();
        for (Problem problem : problemList) {
            problemDtoList.add(new ProblemDto(problem.getId(), problem.getTaskName(), problem.getTimeLimit(), problem.getMemoryLimit()));
        }
        return problemDtoList;
    }

    public ProblemDetailDto getProblemDto(Long id) {
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent())
            return null;
        Problem problem = optionalProblem.get();
        List<SampleCase> sampleCases = problem.getSampleCases();
        List<SampleCaseDto> sampleCaseDtoList = new ArrayList<>();
        for (SampleCase sampleCase : sampleCases) {
            sampleCaseDtoList.add(new SampleCaseDto(sampleCase.getId(), sampleCase.getSampleInput(), sampleCase.getSampleOutput()));
        }
        ProblemDetailDto problemDetailDto = new ProblemDetailDto(problem.getId(), problem.getTaskName(), problem.getTimeLimit(),
                problem.getMemoryLimit(), problem.getProblemStatement(), problem.getProblemConstraint(), sampleCaseDtoList);
        return problemDetailDto;
    }

}
