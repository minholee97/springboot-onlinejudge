package com.algorithm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SampleCaseDto {
    private Long id;
    private Long problemId;
    private String sampleInput;
    private String sampleOutput;

    public SampleCaseDto(Long id, String sampleInput, String sampleOutput) {
        this.id = id;
        this.sampleInput = sampleInput;
        this.sampleOutput = sampleOutput;
    }
}
