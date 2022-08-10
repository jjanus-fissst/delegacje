package com.lbd.projectlbd.controller;

import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.dto.UpdateDelegationDto;
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
        return ResponseEntity.ok().body(
                delegationService.getAll()
        );
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<DelegationDto>> getAllDelegationsPaginated(
            @RequestParam(value = "size",defaultValue ="50",required = false) Integer size,
            @RequestParam(value="page",defaultValue ="1",required = false) Integer page,
            @RequestParam(value="sort",defaultValue ="id",required = false) String sort,
            @RequestParam(value="order",defaultValue ="desc",required = false) String order
    ){
        List<DelegationDto> foundDelegations = delegationService.getAllPaginated(size,page,sort,order)
                .stream()
                .map(delegation -> mapper.mapDelegationToDelegationDTO(delegation))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(foundDelegations);
    }

    @PutMapping("/{delegationId}")
    public ResponseEntity<StandardResponse> updateDelegationById(@PathVariable Long delegationId,
                                                                 @Valid @RequestBody UpdateDelegationDto updateDelegationDto) {
        delegationService.update(delegationId, updateDelegationDto);
        return new StandardResponse(HttpStatus.OK, "Delegation edited").buildResponseEntity();
    }

}
