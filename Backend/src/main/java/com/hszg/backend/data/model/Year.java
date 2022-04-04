package com.hszg.backend.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hszg.backend.data.properties.YearProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    private LocalDate year;
    private String objectName;
    private Boolean exists;

    //TODO: Extract the model and texture into a seperate table
    private Long modelID;
    private Long textureID;

    //TODO: Extract address into a separate table
    private String streetName;
    private String streetNumber;

    private Float latitude;
    private Float longitude;

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    @JsonIgnore
    private Object object;

    public Year() {

    }

    public Year(String year) {
        setYear(year);
    }

    //<editor-fold desc="Getter/Setter">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        //Format: yyyy-mm-dd
        this.year = year;
    }

    public void setYear(String year) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        formatter = formatter.withLocale(Locale.GERMANY);
        this.year = LocalDate.parse(year, formatter);
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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Object getObject() { return object; }

    public void setObject(Object object) { this.object = object; }

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

    public Boolean getExists() {
        return exists;
    }

    public void setExists(Boolean exists) {
        this.exists = exists;
    }
    //</editor-fold>

}
