package com.lbd.projectlbd.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "COMMENT_TO_CHECKPOINT")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentToCheckpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")private Long id;
    @Column(name = "checkpoint_comment")private String comment;
    @Column(name = "date")private Date date;
    @ManyToOne
    @JoinColumn(name = "checkpoint_id")
    private Checkpoint checkpoint;

    public CommentToCheckpoint(String comment, Date date) {
        this.comment = comment;
        this.date = date;
    }
}
