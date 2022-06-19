package com.algorithm.controller;

import com.algorithm.dto.CodeDto;
import com.algorithm.dto.TestCaseDto;
import com.algorithm.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class SubmitController {

    @Autowired
    CodeService codeService;

    @GetMapping("/submit/{id}")
    public String submitPage(@PathVariable String id, Model model) {
        model.addAttribute("id", id);
        return "submitPage";
    }

    @PostMapping("/submit")
    public String submitProcess(CodeDto codeDto, Model model) throws IOException {
        boolean preprocessingResult = codeService.preprocessing(codeDto);
        model.addAttribute("name", codeDto.getUserName());
        model.addAttribute("id", codeDto.getProblemId());
        if (!preprocessingResult) {
            model.addAttribute("result", "compile error");
        } else {
            String result = codeService.execute(codeDto);
            model.addAttribute("result", result);
        }
        return "statusPage";
    }

}
