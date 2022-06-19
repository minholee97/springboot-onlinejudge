package com.algorithm.entity;

import com.algorithm.constant.Role;
import com.algorithm.dto.MemberDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member {
    @Id @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true, length = 40)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

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
