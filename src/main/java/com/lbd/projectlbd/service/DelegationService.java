package com.lbd.projectlbd.service;

import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.entity.Delegation;

public interface DelegationService {

    /**
     * Utilities */
    Delegation findById(Long id);

    /**
     * Rest Controller */
    void add(DelegationDto delegationDTO);
    void delete(Long id);
    void edit(Long delegationId, DelegationDto delegationDTO);

}
