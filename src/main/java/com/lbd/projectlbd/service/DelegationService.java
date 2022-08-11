package com.lbd.projectlbd.service;

import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.dto.UpdateDelegationDto;
import com.lbd.projectlbd.entity.Delegation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DelegationService {

    /**
     * Utilities */
    Delegation findById(Long id);

    /**
     * Rest Controller */
    void add(DelegationDto delegationDTO);
    void delete(Long id);
    void update(Long delegationId, UpdateDelegationDto updateDelegationDto);
    List<DelegationDto> getAll();

    Page<Delegation> getAllPaginated(Integer size, Integer page, String sort, String order);

}
