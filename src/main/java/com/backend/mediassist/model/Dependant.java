package com.backend.mediassist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dependants")
public class Dependant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Integer age;
    private String gender; // "Male", "Female", "Other"
    private String relation; // "Spouse", "Child", "Parent", "Sibling"
    private Long dependantFor; // Employee ID
    
    // Getters and Setters
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
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getRelation() {
        return relation;
    }
    
    public void setRelation(String relation) {
        this.relation = relation;
    }
    
    public Long getDependantFor() {
        return dependantFor;
    }
    
    public void setDependantFor(Long dependantFor) {
        this.dependantFor = dependantFor;
    }
}
