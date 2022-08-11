package com.lbd.projectlbd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbd.projectlbd.validator.IsCountry.IsCountry;
import com.lbd.projectlbd.validator.TimestampValid.TimestampValid;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDate;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class DelegationDto {

    @TimestampValid(shouldBeInFuture = true)
    private LocalDate startDate;

    @TimestampValid(shouldBeInFuture = true)
    private LocalDate endDate;

    private String name;
    private String lastname;
    private String location;
    private String description;


}
