package com.algorithm.controller;

import com.algorithm.dto.ProblemDto;
import com.algorithm.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminContoller {

    private final ProblemService problemService;

    @GetMapping("/admin/problem")
    public String problemListPage(Model model, @PageableDefault(size = 20) Pageable pageable) {
        Page<ProblemDto> problemDtoList = problemService.getProblemDtoList(pageable);
        int startPage = Math.max(1, problemDtoList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(problemDtoList.getTotalPages(), problemDtoList.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
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
    @PostMapping("/admin/registerProblem")
    public String registerProblem(@RequestBody ProblemDto problemDto) {
        problemService.setProblem(problemDto);
        return "success";
    }

    @ResponseBody
    @PutMapping("/admin/updateProblem")
    public String updateProblem(@RequestBody ProblemDto problemDto) {
        problemService.putProblem(problemDto);
        return "success";
    }

    @ResponseBody
    @DeleteMapping("/admin/deleteProblem/{id}")
    public String deleteProblem(@PathVariable String id) {
        problemService.deleteProblem(id);
        return "success";
    }

}
