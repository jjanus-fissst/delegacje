package com.lbd.projectlbd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbd.projectlbd.validator.NotNullAtLeastOne.NotNullAtLeastOne;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)  // universal DTO mapper (hide null fields)
@NotNullAtLeastOne(fieldNames = {"delegationId", "parentId"})   // at least one id must be set
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class CommentDto {
    private Long delegationId;  // comment added to delegation
    private Long parentId;      // comment added to another comment

    @NotNull private String author;
    @NotNull private Timestamp date;
    @NotNull private String content;
}
