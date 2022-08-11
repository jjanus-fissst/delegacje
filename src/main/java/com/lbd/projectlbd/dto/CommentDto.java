package com.lbd.projectlbd.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class CommentDto {
    private Long id;
    private Long delegationId;  // comment added to delegation
    private Long parentId;      // comment added to another comment
    private String title;
    private String author;
    private Timestamp date;
    private String content;
}
