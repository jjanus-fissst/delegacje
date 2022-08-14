package com.lbd.projectlbd.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter @Getter
public class CheckpointDto {
    private Long id;
    private Long delegationId;
    private Long masterDataCheckpointId;
    private List<CommentToCheckpointDto> commentToCheckpointDtos;
    private String description;
    private Boolean isChecked = false;
}
