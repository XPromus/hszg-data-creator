package com.hszg.backend.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "privilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "privileges"
    )
    @JsonIgnore
    private Set<EditorUser> editorUsers = new HashSet<>();

    public Privilege() {

    }

    public Privilege(String name) {
        this.name = name;
    }

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

    public Set<EditorUser> getEditorUsers() {
        return editorUsers;
    }

    public void setEditorUsers(Set<EditorUser> editorUsers) {
        this.editorUsers = editorUsers;
    }

}
