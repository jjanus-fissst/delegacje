package com.lbd.projectlbd.controller;

import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.mapper.DelegationMapper;
import com.lbd.projectlbd.service.DelegationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/delegation")
public class DelegationController {

    @Autowired
    DelegationService delegationService;

    @Autowired
    DelegationMapper mapper;

    @PostMapping
    public ResponseEntity<StandardResponse> addDelegation(@Valid @RequestBody DelegationDto delegationDTO){
        delegationService.add(delegationDTO);
        return new StandardResponse(HttpStatus.OK, "Delegation added").buildResponseEntity();
    }

    @DeleteMapping("/{delegationId}")
    public ResponseEntity<StandardResponse> deleteDelegation(@PathVariable Long delegationId){
        delegationService.delete(delegationId);
        return new StandardResponse(HttpStatus.OK, "Delegation deleted").buildResponseEntity();
    }

    @GetMapping("/{delegationId}")
    public ResponseEntity<DelegationDto> getDelegationById(@PathVariable("delegationId") Long delegationId){
        DelegationDto foundDelegation = mapper.mapDelegationToDelegationDTO(delegationService.findById(delegationId));
        return ResponseEntity.ok().body(foundDelegation);
    }

    @GetMapping()
    public ResponseEntity<List<DelegationDto>> getAllDelegations(){
        List<DelegationDto> foundDelegations = delegationService.getAll()
                .stream()
                .map(delegation -> mapper.mapDelegationToDelegationDTO(delegation))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(foundDelegations);
    }

    @PutMapping("/{delegationId}")
    public ResponseEntity<StandardResponse> editDelegationById(@PathVariable Long delegationId, @RequestBody DelegationDto delegationDTO) {
        delegationService.edit(delegationId, delegationDTO);
        return new StandardResponse(HttpStatus.OK, "Delegation edited").buildResponseEntity();
    }

}
