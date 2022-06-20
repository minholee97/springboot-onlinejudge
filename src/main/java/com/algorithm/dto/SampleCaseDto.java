package com.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SampleCaseDto {
    private Long id;
    private String sampleInput;
    private String sampleOutput;
}
