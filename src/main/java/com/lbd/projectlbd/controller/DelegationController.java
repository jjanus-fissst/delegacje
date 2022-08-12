package com.lbd.projectlbd.controller;

import com.lbd.projectlbd.api.DelegationsApi;
import com.lbd.projectlbd.api.model.DelegationListModelApi;
import com.lbd.projectlbd.api.model.DelegationListV2ModelApi;
import com.lbd.projectlbd.api.model.DelegationModelApi;
import com.lbd.projectlbd.api.model.DelegationV2ModelApi;
import com.lbd.projectlbd.apiresponse.StandardResponse;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.mapper.DelegationMapper;
import com.lbd.projectlbd.service.DelegationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DelegationController implements DelegationsApi {

    private final DelegationService delegationService;
    private final DelegationMapper delegationMapper;

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
                        .map(delegationMapper::mapDelegationDtoToDelegationModelApi)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<List<DelegationV2ModelApi>> getAllDelegationsV2() {
        return ResponseEntity.ok().body(
                delegationService.getAll()
                        .stream()
                        .map(delegationMapper::mapDelegationDtoToDelegationV2ModelApi)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<DelegationListModelApi> getPaginated(Integer size, Integer page, String sort, String order) {
        return ResponseEntity.ok()
                .body(delegationMapper
                        .mapDelegationListDtoToDelegationListModelApi(delegationService
                                .getAllPaginated(size,page,sort,order)));
    }

    @Override
    public ResponseEntity<DelegationListV2ModelApi> getPaginatedV2(Integer size, Integer page, String sort, String order) {
        return ResponseEntity.ok()
                .body(delegationMapper
                        .mapDelegationListDtoToDelegationListV2ModelApi(delegationService
                                .getAllPaginated(size,page,sort,order)));
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
}
