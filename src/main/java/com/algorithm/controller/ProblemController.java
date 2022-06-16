package com.algorithm.controller;

import com.algorithm.dto.ProblemDto;
import com.algorithm.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProblemController {

    @Autowired
    ProblemService problemService;

    @GetMapping("/problem")
    public String problemListPage(Model model) {
        List<ProblemDto> problemDtoList = problemService.getProblemDtoList();
        model.addAttribute("problemDtoList", problemDtoList);
        return "problemListPage";
    }

    @GetMapping("/problem/{id}")
    public String problemPage(@PathVariable String id) {
        return "problemPage";
    }

}
