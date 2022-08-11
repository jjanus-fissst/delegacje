package com.lbd.projectlbd.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "MASTERDATA_CHECKPOINT")
public class MasterdataCheckpoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")              private Long id;
    @Column(name = "spel_expression") private String spelExpression;
    @Column(name = "description")     private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MasterdataCheckpoint that = (MasterdataCheckpoint) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
