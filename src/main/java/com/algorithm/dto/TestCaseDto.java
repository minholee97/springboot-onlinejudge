package com.algorithm.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseDto {
    private Long id;
    private String inputData;
    private String outputData;
}
