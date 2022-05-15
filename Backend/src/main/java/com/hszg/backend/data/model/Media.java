package com.hszg.backend.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hszg.backend.data.properties.MediaProperties;

import javax.persistence.*;

@Entity
@Table(name = "media")
public class Media implements MediaProperties {

    @Id
    @SequenceGenerator(
            name = "media_service",
            sequenceName = "media_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "media_sequence"
    )
    @Column(name = "id")
    private Long id;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
