package com.lbd.projectlbd.service;

import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.entity.Delegation;

import java.util.List;

public interface DelegationService {

    /**
     * Utilities */
    Delegation findById(Long id);

    /**
     * Rest Controller */
    void add(DelegationDto delegationDTO);
    void delete(Long id);
    void edit(Long delegationId, DelegationDto delegationDTO);
    List<Delegation> getAll();

    List<Delegation> getAllPaginated(Integer size,Integer page,String sort,String order);

}
