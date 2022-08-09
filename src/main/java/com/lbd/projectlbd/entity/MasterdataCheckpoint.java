package com.lbd.projectlbd.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "MASTERDATA_CHECKPOINT")
public class MasterdataCheckpoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")              private Long id;
    @Column(name = "spel_expression") private String spelExpression;
    @Column(name = "description")     private String description;

}
