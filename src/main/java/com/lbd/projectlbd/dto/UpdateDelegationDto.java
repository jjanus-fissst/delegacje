package com.lbd.projectlbd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbd.projectlbd.validator.IsCountry.IsCountry;
import com.lbd.projectlbd.validator.TimestampValid.TimestampValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)  // universal DTO mapper (hide null fields)
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class UpdateDelegationDto {

    @TimestampValid(shouldBeInFuture = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp startDate;

    @TimestampValid(shouldBeInFuture = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp endDate;

    private String name;
    private String lastname;
    private String city;
    @IsCountry private String countryCode;
    private String description;

}
