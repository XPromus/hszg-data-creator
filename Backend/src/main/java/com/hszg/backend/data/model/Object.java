package com.hszg.backend.data.model;

import com.hszg.backend.data.properties.ObjectProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "object")
public class Object implements ObjectProperties {

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
    private Float latitude;
    private Float longitude;

    @OneToMany(mappedBy = "object")
    private Set<Year> years;

    @OneToMany(mappedBy = "object")
    private Set<Image> images;

    //<editor-fold desc="Constructor">
    public Object() {

    }

    public Object(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Object(String name, Float latitude, Float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    //</editor-fold>

}
