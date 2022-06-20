package com.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProblemDetailDto {
    private Long id;
    private String taskName;
    private int timeLimit;
    private int memoryLimit;
    private String problemStatement;
    private String problemConstraint;
    private List<SampleCaseDto> sampleCaseDtoList;
}
