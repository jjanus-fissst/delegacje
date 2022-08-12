package com.lbd.projectlbd.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COMMENT_TO_CHECKPOINT")
@Setter
@Getter
public class CommentToCheckpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")private Long id;
    @Column(name = "checkpoint_comment")private String comment;

    @ManyToOne
    @JoinColumn(name = "checkpoint_id")
    private Checkpoint checkpoint;
}
