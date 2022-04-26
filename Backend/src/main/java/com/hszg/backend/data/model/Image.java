package com.hszg.backend.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hszg.backend.data.properties.ImageProperties;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image implements ImageProperties {

    @Id
    @SequenceGenerator(
            name = "image_service",
            sequenceName = "image_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_sequence"
    )
    @Column(name = "id")
    private Long id;

    private String imagePath;
    private String imageDescription;

    @ManyToOne
    @JoinColumn(name = "object_id", nullable = false)
    @JsonIgnore
    private Object object;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
