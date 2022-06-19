package com.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TestCaseDto {
    private Long id;
    private Long problemId;
    private String inputData;
    private String outputData;
}
