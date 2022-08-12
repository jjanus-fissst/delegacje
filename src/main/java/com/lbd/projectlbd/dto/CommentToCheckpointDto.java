package com.lbd.projectlbd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
@AllArgsConstructor
public class CommentToCheckpointDto {
    private Long id;
    private String comment;
    private Long checkpointId;
}
