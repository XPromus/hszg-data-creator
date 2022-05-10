package com.hszg.backend.data.model;

import com.hszg.backend.data.properties.EditorUserProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "editorUser")
public class EditorUser implements EditorUserProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "editorUser_privilege",
            joinColumns = @JoinColumn(name = "editorUser_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id")
    )
    private Set<Privilege> privileges = new HashSet<>();

    public EditorUser() {

    }

    public EditorUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
        privilege.getEditorUsers().add(this);
    }

    public void removePrivilege(Privilege privilege) {
        this.privileges.remove(privilege);
        privilege.getEditorUsers().remove(this);
    }

    public boolean hasPrivilege(Privilege privilege) {
        return privileges.contains(privilege);
    }

}
