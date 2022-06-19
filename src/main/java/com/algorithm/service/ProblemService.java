package com.algorithm.service;

import com.algorithm.dto.ProblemDto;
import com.algorithm.entity.Problem;
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
            problemDtoList.add(new ProblemDto(problem.getId(), problem.getTaskName(), problem.getTimeLimit(),
                    problem.getMemoryLimit(), problem.getProblemStatement(), problem.getProblemConstraint(), problem.getSampleInput(),problem.getSampleOutput()));
        }
        return problemDtoList;
    }

    public ProblemDto getProblemDto(Long id) {
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent())
            return null;
        Problem problem = optionalProblem.get();
        ProblemDto problemDto = new ProblemDto(problem.getId(), problem.getTaskName(), problem.getTimeLimit(),
                problem.getMemoryLimit(), problem.getProblemStatement(), problem.getProblemConstraint(), problem.getSampleInput(),problem.getSampleOutput());
        return problemDto;
    }

}
