package com.lbd.projectlbd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbd.projectlbd.entity.Comment;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)  // universal DTO mapper (hide null fields)
@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class DelegationDTO {

    // TODO wybrac co ma byc nie null (do validacji)
    @NotNull private Timestamp startDate;
    @NotNull private Timestamp endDate;
    @NotNull private String name;
    @NotNull private String lastname;
    @NotNull private String city;
    @NotNull private String countryCode;  // Arrays.asList(Locale.getISOCountries()).contains("DE")
    @NotNull private String description;


}
