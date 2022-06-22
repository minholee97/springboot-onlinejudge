package com.algorithm.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="test_case")
@Getter
@Setter
@ToString(exclude = {"problem"})
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    @Id
    @Column(name = "test_case_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String inputData;

    @Column(nullable = false)
    private String outputData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;
}

