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
            List<Object[]> result = statusRepository.findStatusTypeById(id);
            String statusType = String.valueOf(result.get(0)[0]);
            String progress = String.valueOf(((Float)result.get(0)[1]).intValue());
            if (statusType.equals("IN_PROGRESS"))
                statusType += " (" + progress + "%)";
            typeList.add(statusType);
        }
        return typeList;
    }
}
