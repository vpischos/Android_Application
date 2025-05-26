package com.example.uni_student.models;

public class Grade {
    private String Subject_name;
    private String grade;

    public Grade(String Subject_name, String grade) {
        this.Subject_name = Subject_name;
        this.grade = grade;
    }

    public String getGradeNumber() {
            return grade;
        }
    public String getSubject_name() {
        return Subject_name;
    }
}
