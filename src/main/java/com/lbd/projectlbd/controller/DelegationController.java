package com.lbd.projectlbd.controller;

import com.lbd.projectlbd.api.DelegationsApi;
import com.lbd.projectlbd.api.model.DelegationModelApi;
import com.lbd.projectlbd.api.model.DelegationV2ModelApi;
import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.mapper.DelegationMapper;
import com.lbd.projectlbd.service.DelegationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/** OpenApi Idea
 * controller -> modelAPI
 *     mapApiToDto()            mapDtoToApi()
 * service -> dto
 *     mapDtoToEntity()         mapEnittyToDto()
 * repository -> entity
 * */
@RestController

public class DelegationController implements DelegationsApi {
    @Autowired DelegationService delegationService;
    @Autowired DelegationMapper delegationMapper;


    @Override
    public ResponseEntity<Void> createDelegation(DelegationModelApi delegationModelApi) {
        DelegationDto delegationDto = delegationMapper.mapDelegationModelApiToDelegationDto(delegationModelApi);

        delegationService.add(delegationDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createDelegationV2(DelegationV2ModelApi delegationV2ModelApi) {
        DelegationDto delegationDto = delegationMapper.mapDelegationModelV2ApiToDelegationDto(delegationV2ModelApi);

        delegationService.add(delegationDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<DelegationModelApi> getDelegation(Long delegationId) {
        return ResponseEntity.ok()
                .body(delegationMapper
                        .mapDelegationDtoToDelegationModelApi(delegationService
                                .findDtoById(delegationId)));
    }
    @Override
    public ResponseEntity<DelegationV2ModelApi> getDelegationV2(Long delegationId) {
        return ResponseEntity.ok()
                .body(delegationMapper
                        .mapDelegationDtoToDelegationV2ModelApi(delegationService
                                .findDtoById(delegationId)));
    }

    @Override
    public ResponseEntity<List<DelegationModelApi>> getAllDelegations() {
        return ResponseEntity.ok().body(
                delegationService.getAll()
                        .stream()
                        .map(delegation -> delegationMapper.mapDelegationDtoToDelegationModelApi(delegation))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<List<DelegationV2ModelApi>> getAllDelegationsV2() {
        return ResponseEntity.ok().body(
                delegationService.getAll()
                        .stream()
                        .map(delegation -> delegationMapper.mapDelegationDtoToDelegationV2ModelApi(delegation))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<Void> deleteDelegation(Long delegationId) {
        delegationService.delete(delegationId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteDelegationV2(Long delegationId) {
        delegationService.delete(delegationId);
        return ResponseEntity.ok().build();
    }

    /** Update
     * */
    @Override public ResponseEntity updateDelegation(Long delegationId, DelegationModelApi delegationModelApi) {
        DelegationDto delegationDto = delegationMapper.mapDelegationModelApiToDelegationDto(delegationModelApi);
        delegationService.update(delegationId, delegationDto);
        return new StandardResponse(HttpStatus.OK, "Delegation updated").buildResponseEntity();
    }

    @Override public ResponseEntity updateDelegationV2(Long delegationId, DelegationV2ModelApi delegationV2ModelApi) {
        DelegationDto delegationDto = delegationMapper.mapDelegationModelV2ApiToDelegationDto(delegationV2ModelApi);
        delegationService.update(delegationId, delegationDto);
        return new StandardResponse(StandardResponse.ApiVersion.v2, HttpStatus.OK, "Delegation updated").buildResponseEntity();
    }

//    @PostMapping
//    public ResponseEntity<StandardResponse> addDelegation(@Valid @RequestBody DelegationDto delegationDTO){
//        delegationService.add(delegationDTO);
//        return new StandardResponse(HttpStatus.OK, "Delegation added").buildResponseEntity();
//    }
//    @DeleteMapping("/{delegationId}")
//    public ResponseEntity<StandardResponse> deleteDelegationPrev(@PathVariable Long delegationId){
//        delegationService.delete(delegationId);
//        return new StandardResponse(HttpStatus.OK, "Delegation deleted").buildResponseEntity();
//    }
//    @GetMapping("/{delegationId}")
//    public ResponseEntity<DelegationDto> getDelegationById(@PathVariable("delegationId") Long delegationId){
//        DelegationDto foundDelegation = mapper.mapDelegationToDelegationDTO(delegationService.findById(delegationId));
//        return ResponseEntity.ok().body(foundDelegation);
//    }
//
//    @GetMapping()
//    public ResponseEntity<List<DelegationDto>> getAllDelegations(){
//        return ResponseEntity.ok().body(
//                delegationService.getAll()
//        );
//    }
//    @GetMapping("/paginated")
//    public ResponseEntity<Page<DelegationDto>> getAllDelegationsPaginated(
//            @RequestParam(value = "size",defaultValue ="50",required = false) Integer size,
//            @RequestParam(value="page",defaultValue ="1",required = false) Integer page,
//            @RequestParam(value="sort",defaultValue ="id",required = false) String sort,
//            @RequestParam(value="order",defaultValue ="desc",required = false) String order
//    ){
//        Page<DelegationDto> foundDelegations = delegationService.getAllPaginated(size,page,sort,order)
//                .map(x-> mapper.mapDelegationToDelegationDTO(x));
//
//        return ResponseEntity.ok().body(foundDelegations);
//    }

//    @PutMapping("/{delegationId}")
//    public ResponseEntity<StandardResponse> updateDelegationById(@PathVariable Long delegationId,
//                                                                 @Valid @RequestBody UpdateDelegationDto updateDelegationDto) {
//        delegationService.update(delegationId, updateDelegationDto);
//        return new StandardResponse(HttpStatus.OK, "Delegation edited").buildResponseEntity();
//    }
}
