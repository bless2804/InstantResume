package com.example.resumebuilder.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "resumes")
public class Resume {

    // id is automatically generated for each resume record
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // name that appears at the top of the resume
    private String name;

    // phone number shown in contact info
    private String phone;

    // address or city shown below name
    private String address;

    // new field to store user's email address
    private String email;

    // summary section text
    @Column(length = 3000)
    private String summary;

    // education details
    @Column(length = 3000)
    private String education;

    // experience section, includes job titles and descriptions
    @Column(length = 6000)
    private String experience;

    // list of skills
    @Column(length = 3000)
    private String skills;

    // list of project names or short descriptions
    @Column(length = 3000)
    private String projects;

    // link to the user that owns this resume
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // getter and setter for id
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // getter and setter for name
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // getter and setter for phone
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // getter and setter for address
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // getter and setter for email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // getter and setter for summary
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    // getter and setter for education
    public String getEducation() { return education; }
    public void setEducation(String education) { this.education = education; }

    // getter and setter for experience
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }

    // getter and setter for skills
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    // getter and setter for projects
    public String getProjects() { return projects; }
    public void setProjects(String projects) { this.projects = projects; }

    // getter and setter for user
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
