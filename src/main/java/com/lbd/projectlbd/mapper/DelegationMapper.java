package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.entity.Delegation;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DelegationMapper {


    @Named("mapDelegationToDelegationDTO")
    DelegationDto mapDelegationToDelegationDTO(Delegation source);
    @IterableMapping(qualifiedByName = "mapDelegationToDelegationDTO")
    @Named("mapDelegationSetToDelegationDtoSet")
    List<DelegationDto> mapDelegationListToDelegationDtoList(List<Delegation> source);

    @Named("mapDelegationDtoToDelegation")
    Delegation mapDelegationDtoToDelegation(DelegationDto source);
    @IterableMapping(qualifiedByName = "mapDelegationDtoToDelegation")
    @Named("mapDelegationDtoSetToDelegationSet")
    List<Delegation> mapDelegationDtoListToDelegationList(List<DelegationDto> source);

}