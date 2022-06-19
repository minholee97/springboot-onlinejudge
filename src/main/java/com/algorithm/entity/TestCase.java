package com.algorithm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="test_case")
@Getter
@Setter
@ToString
public class TestCase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private String inputData;

    @Column(nullable = false)
    private String outputData;
}
