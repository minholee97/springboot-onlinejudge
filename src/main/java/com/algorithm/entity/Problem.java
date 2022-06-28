package com.algorithm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="problem")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Problem {

    @Id @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private int timeLimit;

    private int memoryLimit;

    @Lob
    private String problemStatement;

    @Lob
    private String problemConstraint;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    List<SampleCase> sampleCases = new ArrayList<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    List<TestCase> testCases = new ArrayList<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    List<Status> statuses = new ArrayList<>();
}
