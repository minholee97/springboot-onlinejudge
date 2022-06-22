package com.algorithm.dto;

import com.algorithm.entity.SampleCase;
import com.algorithm.entity.TestCase;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProblemDto {
    private Long id;
    private String taskName;
    private int timeLimit;
    private int memoryLimit;
    private String problemStatement;
    private String problemConstraint;
    private List<SampleCaseDto> sampleCaseDtos;
    private List<TestCaseDto> testCaseDtos;

    public ProblemDto(Long id, String taskName, int timeLimit, int memoryLimit) {
        this.id = id;
        this.taskName = taskName;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
    }

}
