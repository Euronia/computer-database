package com.excilys.formation.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * Created by excilys on 04/01/17.
 */
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String role;
    @ManyToMany(mappedBy = "authorities")
    private List<User> users;

    private Role() {
    }

    private Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role1 = (Role) o;

        if (id != null ? !id.equals(role1.id) : role1.id != null) return false;
        if (role != null ? !role.equals(role1.role) : role1.role != null) return false;
        return users != null ? users.equals(role1.users) : role1.users == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}
