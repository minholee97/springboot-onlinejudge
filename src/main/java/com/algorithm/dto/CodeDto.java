package com.algorithm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CodeDto {
    private Long problemId;
    private String lang;
    private String memberCode;
    private String memberEmail;
}
