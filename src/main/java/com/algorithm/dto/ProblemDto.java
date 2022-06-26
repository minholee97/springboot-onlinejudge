package com.algorithm.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemDto {
    private Long id;
    private String taskName;
    private int timeLimit;
    private int memoryLimit;
    private String problemStatement;
    private String problemConstraint;
    private List<SampleCaseDto> sampleCases;
    private List<TestCaseDto> testCases;

    public ProblemDto(Long id, String taskName, int timeLimit, int memoryLimit) {
        this.id = id;
        this.taskName = taskName;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

    public ProblemDto(Long id, String taskName, int timeLimit, int memoryLimit, String problemStatement, String problemConstraint) {
        this.id = id;
        this.taskName = taskName;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.problemStatement = problemStatement;
        this.problemConstraint = problemConstraint;
    }
}
