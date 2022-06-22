package com.algorithm.service;

import com.algorithm.constant.StatusType;
import com.algorithm.dto.StatusDto;
import com.algorithm.entity.Status;
import com.algorithm.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Page<StatusDto> getStatusDtoListByProblemId(Long problemId, Pageable pageable) {
        Page<StatusDto> statusList = statusRepository.findAllByProblemIdOrderByIdDesc(problemId, pageable);
        return statusList;
    }

    public List<String> getStatusTypeList(List<String> idList) {
        List<String> typeList = new ArrayList<>();
        for (String id : idList) {
            String statusType = statusRepository.findStatusTypeById(id);
            typeList.add(statusType);
        }
        return typeList;
    }
}
