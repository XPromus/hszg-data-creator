package com.hszg.backend.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hszg.backend.data.properties.YearProperties;

import javax.persistence.*;

@Entity
@Table(name = "years")
public class Year implements YearProperties {

    @Id
    @SequenceGenerator(
            name = "year_service",
            sequenceName = "year_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "year_sequence"
    )
    @Column(name = "id")
    private Long id;

    private Integer year;
    private String objectName;
    private Boolean exists = true;
    private Long modelID;
    private Long textureID;
    private String streetName;
    private Integer streetNumber;

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    @JsonIgnore
    private Object object;

    public Year() {

    }

    public Year(Integer year) {
        setYear(year);
    }

    //<editor-fold desc="Getter/Setter">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Long getModelID() {
        return modelID;
    }

    public void setModelID(Long modelID) {
        this.modelID = modelID;
    }

    public Long getTextureID() {
        return textureID;
    }

    public void setTextureID(Long textureID) {
        this.textureID = textureID;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Object getObject() { return object; }

    public void setObject(Object object) { this.object = object; }

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }
    //</editor-fold>

}
