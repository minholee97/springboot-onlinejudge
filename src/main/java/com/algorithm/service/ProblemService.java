package com.algorithm.service;

import com.algorithm.dto.ProblemDto;
import com.algorithm.dto.SampleCaseDto;
import com.algorithm.dto.TestCaseDto;
import com.algorithm.entity.Problem;
import com.algorithm.entity.SampleCase;
import com.algorithm.entity.TestCase;
import com.algorithm.repository.ProblemRepository;
import com.algorithm.repository.SampleCaseRepository;
import com.algorithm.repository.TestCaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final SampleCaseRepository sampleCaseRepository;
    private final TestCaseRepository testCaseRepository;


    public List<ProblemDto> getProblemDtoList() {
        List<Problem> problemList = problemRepository.findAll();
        List<ProblemDto> problemDtoList = new ArrayList<>();
        for (Problem problem : problemList) {
            problemDtoList.add(new ProblemDto(problem.getId(), problem.getTaskName(), problem.getTimeLimit(), problem.getMemoryLimit()));
        }
        return problemDtoList;
    }

    public ProblemDto getProblemDto(Long id, boolean manage) {
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent())
            return null;
        Problem problem = optionalProblem.get();
        List<SampleCase> sampleCases = problem.getSampleCases();
        List<SampleCaseDto> sampleCaseDtoList = new ArrayList<>();
        for (SampleCase sampleCase : sampleCases) {
            sampleCaseDtoList.add(new SampleCaseDto(sampleCase.getId(), sampleCase.getSampleInput(), sampleCase.getSampleOutput()));
        }
        if (manage) {
            List<TestCase> testCases = problem.getTestCases();
            List<TestCaseDto> testCaseDtoList = new ArrayList<>();
            for (TestCase testCase : testCases) {
                testCaseDtoList.add(new TestCaseDto(testCase.getId(), testCase.getInputData(), testCase.getOutputData()));
            }
            return new ProblemDto(problem.getId(), problem.getTaskName(), problem.getTimeLimit(),
                    problem.getMemoryLimit(), problem.getProblemStatement(), problem.getProblemConstraint(), sampleCaseDtoList, testCaseDtoList);
        }
        else
            return new ProblemDto(problem.getId(), problem.getTaskName(), problem.getTimeLimit(),
                problem.getMemoryLimit(), problem.getProblemStatement(), problem.getProblemConstraint(), sampleCaseDtoList, null);

    }
    public void setProblem(ProblemDto problemDto) {
        List<SampleCase> sampleCases = new ArrayList<>();
        List<TestCase> testCases = new ArrayList<>();
        Problem problem = new Problem(problemDto.getId(), problemDto.getTaskName(), problemDto.getTimeLimit(), problemDto.getMemoryLimit(), problemDto.getProblemStatement(), problemDto.getProblemConstraint(), null, null);
        problemRepository.save(problem);
        for (SampleCaseDto sampleCaseDto : problemDto.getSampleCaseDtos()) {
            SampleCase sampleCase = new SampleCase(sampleCaseDto.getId(), sampleCaseDto.getSampleInput(), sampleCaseDto.getSampleOutput(), problem);
            sampleCaseRepository.save(sampleCase);
            sampleCases.add(sampleCase);
        }
        for (TestCaseDto testCaseDto : problemDto.getTestCaseDtos()) {
            TestCase testCase = new TestCase(testCaseDto.getId(), testCaseDto.getInputData(), testCaseDto.getOutputData(), problem);
            testCaseRepository.save(testCase);
            testCases.add(testCase);
        }
        //problem = new Problem(problemDto.getId(), problemDto.getTaskName(), problemDto.getTimeLimit(), problemDto.getMemoryLimit(), problemDto.getProblemStatement(), problemDto.getProblemConstraint(), sampleCases, testCases);
    }
}
