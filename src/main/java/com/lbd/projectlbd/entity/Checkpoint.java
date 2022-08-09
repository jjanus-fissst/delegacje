package com.lbd.projectlbd.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "DELEGATION_CHECKLIST")
@Data
public class Checkpoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")            private Long id;
    // checkpoint_id (id from master data)
    @Column(name = "checkpoint_id") private Long masterDataCheckpointId;
    @Column(name = "is_checked")    private Boolean isChecked;
    @Column(name = "description")   private String description;
    @Column(name = "comment")       private String comment;

    @ManyToOne
    @JoinColumn(name = "delegation_id")
    private Delegation delegation;



}
