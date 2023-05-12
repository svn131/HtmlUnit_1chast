package ru.kata.spring.boot_security.demo.entities;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

import java.util.Objects;

@Entity
@Data
@Table(name = "roles", schema = "katashema")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) && Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
