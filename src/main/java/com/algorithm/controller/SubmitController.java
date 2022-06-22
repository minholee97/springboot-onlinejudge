package com.algorithm.controller;

import com.algorithm.dto.CodeDto;
import com.algorithm.dto.StatusDto;
import com.algorithm.dto.TestCaseDto;
import com.algorithm.entity.Status;
import com.algorithm.service.CodeService;
import com.algorithm.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SubmitController {

    private final CodeService codeService;

    @GetMapping("/submit/{id}")
    public String submitPage(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "submitPage";
    }

    @PostMapping("/submit")
    public String submitProcess(CodeDto codeDto, Model model) throws IOException, InterruptedException {
        Status status = codeService.preprocessing(codeDto);
        codeService.processing(codeDto, status);
        return "redirect:/status/" + String.valueOf(codeDto.getProblemId());
    }

}
