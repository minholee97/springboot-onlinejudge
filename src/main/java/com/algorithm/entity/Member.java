package com.algorithm.entity;

import com.algorithm.constant.Role;
import com.algorithm.dto.MemberDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, length = 40)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Status> statusList = new ArrayList<>();


    public Member(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = new Member(memberDto.getName(), memberDto.getEmail(), passwordEncoder.encode(memberDto.getPassword()), Role.USER);
        return member;
    }
}
