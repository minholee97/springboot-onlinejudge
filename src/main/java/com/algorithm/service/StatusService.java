package com.algorithm.service;

import com.algorithm.constant.StatusType;
import com.algorithm.dto.ProblemDto;
import com.algorithm.dto.StatusDto;
import com.algorithm.entity.Problem;
import com.algorithm.entity.Status;
import com.algorithm.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Page<StatusDto> getStatusDtoListByProblemId(Long problemId, Pageable pageable) {
        Page<Status> statusList = statusRepository.findAllByProblemIdOrderByIdDesc(problemId, pageable);

        Page<StatusDto> statusDtosList = statusList.map(new Function<Status, StatusDto>() {
            @Override
            public StatusDto apply(Status status) {
                StatusDto statusDto = new StatusDto(status.getId(), status.getProblemId(), status.getCode(), status.getStatusType(), status.getLanguage(), status.getMember().getEmail());
                return statusDto;
            }
        });
        return statusDtosList;
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
