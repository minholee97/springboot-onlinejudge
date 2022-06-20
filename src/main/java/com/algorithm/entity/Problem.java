package com.algorithm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="problem")
@Getter
@ToString
public class Problem {

    @Id @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String taskName;

    @Column(nullable = false)
    private int timeLimit;

    @Column(nullable = false)
    private int memoryLimit;

    @Lob
    @Column(nullable = false)
    private String problemStatement;

    @Lob
    @Column(nullable = true)
    private String problemConstraint;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    List<SampleCase> sampleCases = new ArrayList<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
    List<TestCase> testCases = new ArrayList<>();
}
