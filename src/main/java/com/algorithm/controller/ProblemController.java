package com.algorithm.controller;

import com.algorithm.dto.ProblemDto;
import com.algorithm.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;

    @GetMapping("/problem")
    public String problemListPage(Model model) {
        List<ProblemDto> problemDtoList = problemService.getProblemDtoList();
        model.addAttribute("problemDtoList", problemDtoList);
        return "problem/problemListPage";
    }

    @GetMapping("/problem/{id}")
    public String problemPage(@PathVariable String id, Model model) {
        ProblemDto problemDto = problemService.getProblemDto(Long.parseLong(id), false);
        if (problemDto == null)
            return "errorPage";
        model.addAttribute("problemDto", problemDto);
        return "problem/problemPage";
    }

}
