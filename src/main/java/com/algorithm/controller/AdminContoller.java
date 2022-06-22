package com.algorithm.controller;

import com.algorithm.dto.ProblemDto;
import com.algorithm.dto.SampleCaseDto;
import com.algorithm.service.ProblemService;
import com.algorithm.service.SampleCaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AdminContoller {

    private final ProblemService problemService;
    private final SampleCaseService sampleCaseService;

    @GetMapping("/admin/problem")
    public String problemListPage(Model model) {
        List<ProblemDto> problemDtoList = problemService.getProblemDtoList();
        model.addAttribute("problemDtoList", problemDtoList);
        return "admin/problemListManagePage";
    }

    @GetMapping("/admin/problem/update/{id}")
    public String problemPage(@PathVariable String id, Model model) {
        ProblemDto problemDto = problemService.getProblemDto(Long.parseLong(id), true);
        System.out.println(problemDto);
        model.addAttribute("problemDto", problemDto);
        model.addAttribute("problemId", id);
        //problemService.getProblemDetailDto(id)
        return "admin/problemUpdatePage";
    }

    @GetMapping("/admin/problem/register")
    public String problemRegister() {
        return "admin/problemRegisterPage";
    }

    @ResponseBody
    @PostMapping("/admin/test")
    public String test(@RequestBody ProblemDto problemDto) {
        System.out.println("======");
        System.out.println(problemDto.getSampleCaseDtos());
        System.out.println("======");
        problemService.setProblem(problemDto);
        return "success";
    }
}
