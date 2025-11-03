package org.example.restapi.models;

public class Dependant {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String relation;
    private Long dependantFor; // Maps to empId in service logic

    // Default constructor for Rest Assured deserialization
    public Dependant() {
    }

    // Constructor for creating a new Dependant (Request Body fields)
    public Dependant(String name, Integer age, String gender, String relation) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.relation = relation;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long dependantId) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
    public Long getDependantFor() { return dependantFor; }
    public void setDependantFor(Long dependantFor) { this.dependantFor = dependantFor; }
}
