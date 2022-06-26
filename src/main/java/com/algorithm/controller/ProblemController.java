package com.algorithm.controller;

import com.algorithm.dto.ProblemDto;
import com.algorithm.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public String problemListPage(Model model,  @PageableDefault(size = 20) Pageable pageable) {
        Page<ProblemDto> problemDtoList = problemService.getProblemDtoList(pageable);
        int startPage = Math.max(1, problemDtoList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(problemDtoList.getTotalPages(), problemDtoList.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("problemDtoList", problemDtoList);
        return "problem/problemListPage";
    }

    @GetMapping("/problem/{id}")
    public String problemPage(@PathVariable String id, Model model) {
        System.out.println("asd");

        ProblemDto problemDto = problemService.getProblemDto(Long.parseLong(id), false);
        if (problemDto == null)
            return "errorPage";
        model.addAttribute("problemDto", problemDto);
        System.out.println(problemDto.toString());
        return "problem/problemPage";
    }

}
