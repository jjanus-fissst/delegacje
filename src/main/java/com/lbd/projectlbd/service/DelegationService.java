package com.lbd.projectlbd.service;

import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.dto.DelegationListDto;
import com.lbd.projectlbd.entity.Delegation;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public interface DelegationService {

    /**
     * Utilities */
    Delegation findById(Long id);
    DelegationDto findDtoById(Long id);

    /**
     * Rest Controller */
    void add(DelegationDto delegationDTO);
    void delete(Long id);
    void update(Long delegationId, @Valid DelegationDto delegationDto);
    List<DelegationDto> getAll();

    DelegationListDto getAllPaginated(Integer size, Integer page, String sort, String order);

}
