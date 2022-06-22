package com.algorithm.controller;

import com.algorithm.dto.MemberDto;
import com.algorithm.entity.Member;
import com.algorithm.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/register")
    public String memberForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/memberRegisterPage";
    }


    @PostMapping(value = "/new")
    public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "member/memberRegisterPage";
        }

        try {
            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberRegisterPage";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember() {
        return "/member/memberLoginPage";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "이메일 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginPage";
    }
}
