package com.lbd.projectlbd.mapper;

import com.lbd.projectlbd.api.model.DelegationModelApi;
import com.lbd.projectlbd.api.model.DelegationV2ModelApi;
import com.lbd.projectlbd.dto.DelegationDto;
import com.lbd.projectlbd.entity.Delegation;
import com.lbd.projectlbd.exception.DelegationValidationException;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DelegationMapper {


    @Named("mapDelegationToDelegationDTO")
    DelegationDto mapDelegationToDelegationDTO(Delegation source);
    @IterableMapping(qualifiedByName = "mapDelegationToDelegationDTO")
    @Named("mapDelegationSetToDelegationDtoSet")
    List<DelegationDto> mapDelegationListToDelegationDtoList(List<Delegation> source);
    @Named("updateDelegation")
    Delegation updateDelegation(@MappingTarget Delegation delegation, DelegationDto delegationDto);

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


    /**
     * DelegationModelApi <-> DelegationDto
     * */
    @Named("mapDelegationModelApiToDelegationDto")
    @Mapping(target = "countryCode",expression = "java(getCountryCode(source.getLocation()))")
    @Mapping(target = "city",expression = "java(getCity(source.getLocation()))")
    DelegationDto mapDelegationModelApiToDelegationDto(DelegationModelApi source);
    @IterableMapping(qualifiedByName = "mapDelegationModelApiToDelegationDto")
    @Named("mapDelegationModelApiListToDelegationDtoList")
    List<DelegationDto> mapDelegationModelApiListToDelegationDtoList(List<DelegationModelApi> source);

    @Named("mapDelegationModelV2ApiToDelegationDto")
    DelegationDto mapDelegationModelV2ApiToDelegationDto(DelegationV2ModelApi source);
    @IterableMapping(qualifiedByName = "mapDelegationModelV2ApiToDelegationDto")
    @Named("mapDelegationModelV2ApiListToDelegationDtoList")
    List<DelegationDto> mapDelegationModelV2ApiListToDelegationDtoList(List<DelegationV2ModelApi> source);

    @Named("getCountryCode")
    default String getCountryCode(String location){
        String[] locationSplit = location.trim().split("\\s*,\\s*");

        if(locationSplit.length != 2){
            throw new DelegationValidationException("Invalid location format");
        }
        return locationSplit[1];
    }

    @Named("getCity")
    default String getCity(String location){
        String[] locationSplit = location.trim().split("\\s*,\\s*");

        if(locationSplit.length != 2){
            throw new DelegationValidationException("Invalid location format");
        }
        return locationSplit[0];
    }

    default String joinCityCountry(String city,String country){
        return (city!=null ? city : "")+","+(country!=null ? country : "");
    }



}