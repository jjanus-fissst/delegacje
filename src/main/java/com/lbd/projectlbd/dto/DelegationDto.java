package com.lbd.projectlbd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbd.projectlbd.validator.IsCountry.IsCountry;
import com.lbd.projectlbd.validator.TimestampValid.TimestampValid;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)  // universal DTO mapper (hide null fields)
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class DelegationDto {

    // TODO wybrac co ma byc nie null (do validacji)
    @NotNull
    @TimestampValid(shouldBeInFuture = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp startDate;

    @NotNull
    @TimestampValid(shouldBeInFuture = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Timestamp endDate;

    @NotNull private String name;
    @NotNull private String lastname;
    @NotNull private String city;
    @NotNull @IsCountry private String countryCode;
    @NotNull private String description;


}
