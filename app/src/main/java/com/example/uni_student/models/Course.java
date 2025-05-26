package com.example.uni_student.models;

public class Course {
    private String Subject_name, name;

    public Course(String Subject_name, String name) {
        this.Subject_name = Subject_name;
        this.name = name;
    }

    public String getPName() {
        return name;
    }
    public String getSubject_name() {
        return Subject_name;
    }
}
