package com.lbd.projectlbd.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "DELEGATION")
public class Delegation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")          private Long id;
    @Column(name = "begin_date")  private Timestamp startDate;
    @Column(name = "end_date")    private Timestamp endDate;
    @Column(name = "first_name")  private String name;
    @Column(name = "last_name")   private String lastname;
    @Column(name = "city")        private String city;
    @Column(name = "country")     private String countryCode;  // Arrays.asList(Locale.getISOCountries()).contains("DE")
    @Column(name = "description") private String description ;

    // Comments for delegation
    @OneToMany(mappedBy = "delegation")
    private List<Comment> commentSet = new ArrayList<>();  // order does matter for comments

    // Checkpoints for delegation
    @OneToMany(mappedBy = "delegation", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Checkpoint> checkpointSet = new ArrayList<>();


    // "orphanRemoval = true" requires to update list instead of replacing
    // https://stackoverflow.com/questions/5587482/hibernate-a-collection-with-cascade-all-delete-orphan-was-no-longer-referenc
    public void setCheckpointSet(List<Checkpoint> checkpointList) {
        this.checkpointSet.clear();
        if (checkpointList != null)
            this.checkpointSet.addAll(checkpointList);
    }


}
