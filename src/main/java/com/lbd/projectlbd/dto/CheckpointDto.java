package com.lbd.projectlbd.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter @Getter
public class CheckpointDto {
    private long id;
    private Long delegationId;
    private Long masterDataCheckpointId;
    private String comment;
    private String description;
    private Boolean isChecked = false;
}
