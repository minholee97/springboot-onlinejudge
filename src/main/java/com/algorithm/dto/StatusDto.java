package com.algorithm.dto;

import com.algorithm.constant.Language;
import com.algorithm.constant.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class StatusDto {
    private Long id;
    private Long problemId;
    private String code;
    private StatusType statusType;
    private Language language;
    private String memberEmail;
}
