package com.algorithm.repository;

import com.algorithm.constant.Role;
import com.algorithm.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원 저장 테스트")
    @Transactional
    public void createMemberTest() {
        Member member = new Member();
        member.setName("test");
        member.setEmail("test@test.com");
        member.setPassword("testtest");
        member.setRole(Role.USER);
        Member savedMember = memberRepository.save(member);
        Optional<Member> findOptionalMember = memberRepository.findById(savedMember.getId());
        Member findMember = findOptionalMember.get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }

}