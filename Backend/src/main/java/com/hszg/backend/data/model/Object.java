package com.hszg.backend.data.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "object")
public class Object {

    @Id
    @SequenceGenerator(
            name = "object_service",
            sequenceName = "object_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "object_sequence"
    )
    private Long id;
    private String name;

    @OneToMany(mappedBy = "object")
    private Set<Year> years;

    //<editor-fold desc="Constructor">
    public Object() {

    }

    public Object(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Object(String name) {
        this.name = name;
    }
    //</editor-fold>

    //<editor-fold desc="Getter/Setter">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Year> getYears() {
        return years;
    }

    public void setYears(Set<Year> years) {
        this.years = years;
    }
    //</editor-fold>

}
