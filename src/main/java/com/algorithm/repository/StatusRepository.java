package com.algorithm.repository;

import com.algorithm.constant.StatusType;
import com.algorithm.dto.StatusDto;
import com.algorithm.entity.Problem;
import com.algorithm.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Page<StatusDto> findAllByProblemIdOrderByIdDesc(Long problemId, Pageable pageable);

    @Query(value="select status_type from status where status_id = :id", nativeQuery = true)
    public String findStatusTypeById(@Param("id") String id);
}
