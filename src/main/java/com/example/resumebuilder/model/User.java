package com.example.resumebuilder.model;

import jakarta.persistence.*;

// this class represents the "users" table in the database
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    // this is the primary key that automatically increases for each new user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // this stores the name of the user
    private String name;

    // this stores the user’s email and makes sure each email is unique and not null
    @Column(nullable = false)
    private String email;

    // this stores the user’s password
    private String password;

    // getter and setter methods used to access and modify private variables
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
