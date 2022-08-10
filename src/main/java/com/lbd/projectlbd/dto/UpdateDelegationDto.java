package com.lbd.projectlbd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbd.projectlbd.validator.IsValidCountry.IsValidCountry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)  // universal DTO mapper (hide null fields)
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class UpdateDelegationDto {

    private Timestamp startDate;
    private Timestamp endDate;
    private String name;
    private String lastname;
    private String city;
    @IsValidCountry private String countryCode;
    private String description;


}
