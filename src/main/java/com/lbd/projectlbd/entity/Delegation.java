package com.lbd.projectlbd.entity;

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

    // Checkpoints for delegation
    @OneToMany(mappedBy = "delegation")
    private List<Checkpoint> checkpointSet = new ArrayList<>();

}
