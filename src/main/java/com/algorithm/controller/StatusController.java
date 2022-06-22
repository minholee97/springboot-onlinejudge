package com.algorithm.controller;

import com.algorithm.dto.StatusDto;
import com.algorithm.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;

    @GetMapping("/status/{id}")
    public String statusPage(@PathVariable String id, Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<StatusDto> statusDtoList = statusService.getStatusDtoListByProblemId(Long.parseLong(id), pageable);
        int startPage = Math.max(1, statusDtoList.getPageable().getPageNumber() - 4);
        int endPage = Math.min(statusDtoList.getTotalPages(), statusDtoList.getPageable().getPageNumber() + 4);
        model.addAttribute("id", id);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("statusDtoList", statusDtoList);
        return "statusPage";
    }

    @ResponseBody
    @PostMapping("/status/reload")
    public List<String> statusReload(@RequestParam(value="idList[]") List<String> idList) {
        List<String> statusList = statusService.getStatusTypeList(idList);
        return statusList;
    }
}
