package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.api.model.DelegationModelApi;
import com.lbd.projectlbd.api.model.DelegationV2ModelApi;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.dto.UpdateDelegationDto;
import com.lbd.projectlbd.entity.Delegation;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DelegationMapper {


    @Named("mapDelegationToDelegationDTO")
    DelegationDto mapDelegationToDelegationDTO(Delegation source);
    @IterableMapping(qualifiedByName = "mapDelegationToDelegationDTO")
    @Named("mapDelegationSetToDelegationDtoSet")
    List<DelegationDto> mapDelegationListToDelegationDtoList(List<Delegation> source);
    @Named("updateDelegation")
    Delegation updateDelegation(@MappingTarget Delegation delegation, UpdateDelegationDto updateDelegationDto);

    @Named("mapDelegationDtoToDelegation")
    Delegation mapDelegationDtoToDelegation(DelegationDto source);
    @IterableMapping(qualifiedByName = "mapDelegationDtoToDelegation")
    @Named("mapDelegationDtoSetToDelegationSet")
    List<Delegation> mapDelegationDtoListToDelegationList(List<DelegationDto> source);

    @Named("mapDelegationDtoToDelegationModelApi")
    @Mapping(target = "location",expression = "java(joinCityCountry(source.getCity(),source.getCountryCode()))")
    DelegationModelApi mapDelegationDtoToDelegationModelApi(DelegationDto source);
    @IterableMapping(qualifiedByName = "mapDelegationDtoToDelegationModelApi")
    @Named("mapDelegationDtoListToDelegationModelApiList")
    List<DelegationModelApi> mapDelegationDtoListToDelegationModelApiList(List<DelegationDto> source);

    @Named("mapDelegationDtoToDelegationV2ModelApi")
    DelegationV2ModelApi mapDelegationDtoToDelegationV2ModelApi(DelegationDto source);
    @IterableMapping(qualifiedByName = "mapDelegationDtoToDelegationV2ModelApi")
    @Named("mapDelegationDtoListToDelegationModelV2ApiList")
    List<DelegationV2ModelApi> mapDelegationDtoListToDelegationModelV2ApiList(List<DelegationDto> source);




    @Named("mapDelegationModelApiToDelegationDto")
    @Mapping(target = "countryCode",expression = "java(splitLocation(source.getLocation(),1))")
    @Mapping(target = "city",expression = "java(splitLocation(source.getLocation(),0))")
    DelegationDto mapDelegationModelApiToDelegationDto(DelegationModelApi source);
    @IterableMapping(qualifiedByName = "mapDelegationModelApiToDelegationDto")
    @Named("mapDelegationModelApiListToDelegationDtoList")
    List<DelegationDto> mapDelegationModelApiListToDelegationDtoList(List<DelegationModelApi> source);

    @Named("mapDelegationModelV2ApiToDelegationDto")
    DelegationDto mapDelegationModelV2ApiToDelegationDto(DelegationV2ModelApi source);
    @IterableMapping(qualifiedByName = "mapDelegationModelV2ApiToDelegationDto")
    @Named("mapDelegationModelV2ApiListToDelegationDtoList")
    List<DelegationDto> mapDelegationModelV2ApiListToDelegationDtoList(List<DelegationV2ModelApi> source);

    default String splitLocation(String location,Integer position){
        String[] locationSplit=location.split(",");
        if(locationSplit.length>0 && locationSplit.length<3){
            return locationSplit[position];
        }
        return null;
    }

    default String joinCityCountry(String city,String country){
        return city!=null ? city : ""+","+country!=null ? country : "";
    }



}