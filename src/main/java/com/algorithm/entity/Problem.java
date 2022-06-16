package com.algorithm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="problem")
@Getter
@Setter
@ToString
public class Problem {

    @Id @Column(name = "problem_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Lob
    @Column(nullable = false)
    private String sampleInput;

    @Lob
    @Column(nullable = false)
    private String sampleOutput;
}
