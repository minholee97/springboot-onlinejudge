package com.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProblemDto {
    private Long id;
    private String taskName;
    private int timeLimit;
    private int memoryLimit;
}
