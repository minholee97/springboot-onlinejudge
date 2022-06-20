package com.algorithm.entity;


import com.algorithm.constant.Language;
import com.algorithm.constant.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    private String memberEmail;

    private Long problemId;

    @Lob
    private String code;

    @Enumerated(EnumType.STRING)
    private StatusType statusType;

    @Enumerated(EnumType.STRING)
    private Language language;

    public Status(String memberEmail, Long problemId, String code, StatusType statusType, Language language) {
        this.memberEmail = memberEmail;
        this.problemId = problemId;
        this.code = code;
        this.statusType = statusType;
        this.language = language;
    }

    public void updateStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

}
