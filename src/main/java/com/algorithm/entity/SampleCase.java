package com.algorithm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="sample_case")
@Getter
@ToString(exclude = {"problem"})
public class SampleCase {
    @Id
    @Column(name = "sample_case_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String sampleInput;

    @Lob
    @Column(nullable = false)
    private String sampleOutput;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
