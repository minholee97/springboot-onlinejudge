package com.algorithm.service;

import com.algorithm.dto.StatusDto;
import com.algorithm.entity.Status;
import com.algorithm.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public List<StatusDto> getStatusDtoListByProblemId(Long problemId) {
        List<Status> statusList = statusRepository.findAllByProblemId(problemId);
        List<StatusDto> statusDtoList = new ArrayList<>();
        for (Status status : statusList) {
            statusDtoList.add(new StatusDto(status.getId(), status.getMemberEmail(), status.getProblemId(), status.getCode(), status.getStatusType(), status.getLanguage()));
        }
        return statusDtoList;
    }
}
