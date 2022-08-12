package com.lbd.projectlbd.dto;

import com.lbd.projectlbd.validator.DateFieldsOrder.DateFieldsOrder;
import com.lbd.projectlbd.validator.IsCountry.IsCountry;
import com.lbd.projectlbd.validator.DateInFuture.DateInFuture;
import lombok.*;

import java.time.LocalDate;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@DateFieldsOrder(dateField = "startDate", dateFieldAfter = "endDate", required = false, message = "endDate should follow startDate")
public class DelegationDto {

    @DateInFuture private LocalDate startDate;
    @DateInFuture private LocalDate endDate;
    private String name;
    private String lastname;
    private String city;
    @IsCountry private String countryCode;
    private String description;

}
