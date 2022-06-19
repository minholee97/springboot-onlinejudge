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
    private String problemId;
    private String lang;
    private String userName;
    private String userCode;
}
