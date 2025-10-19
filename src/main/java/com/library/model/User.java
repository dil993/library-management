package com.library.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @OneToMany(mappedBy = "checkedOutBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> checkedOutBooks = new HashSet<>();

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Set<Book> getCheckedOutBooks() { return checkedOutBooks; }
    public void setCheckedOutBooks(Set<Book> checkedOutBooks) { this.checkedOutBooks = checkedOutBooks; }
}