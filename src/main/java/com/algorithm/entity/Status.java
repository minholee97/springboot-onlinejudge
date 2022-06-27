package com.algorithm.entity;


import com.algorithm.constant.Language;
import com.algorithm.constant.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="status")
@Getter
@Setter
@NoArgsConstructor
public class Status {
    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long problemId;

    @Lob
    private String code;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Enumerated(EnumType.STRING)
    private Language language;

    private float progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    public Status(Long problemId, String code, StatusType statusType, Language language, Member member) {
        this.problemId = problemId;
        this.code = code;
        this.statusType = statusType;
        this.language = language;
        this.member = member;
    }

    public void updateStatus(StatusType statusType, float progress) {
        this.statusType = statusType;
        this.progress = progress;
    }

}
