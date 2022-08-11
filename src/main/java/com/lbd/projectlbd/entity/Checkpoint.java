package com.lbd.projectlbd.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DELEGATION_CHECKLIST")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Checkpoint that = (Checkpoint) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
