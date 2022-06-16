package com.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProblemDto {
    private Long id;
    private String taskName;
    private int timeLimit;
    private int memoryLimit;
    private String problemStatement;
    private String problemConstraint;
    private String sampleInput;
    private String sampleOutput;
}
