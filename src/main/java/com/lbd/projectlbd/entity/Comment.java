package com.lbd.projectlbd.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "COMMENT")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")      private Long id;
    @Column(name = "author")  private String author;
    @Column(name = "title")   private String title;
    @Column(name = "date")    private LocalDate date;
    @Column(name = "content") private String content;

    // Comment to delegation
    @ManyToOne
    @JoinColumn(name = "delegation_id")
    private Delegation delegation;

    // Comment for comment (subcomment)
    @OneToMany(mappedBy = "comment")
    private List<Comment> commentList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment comment;

}
