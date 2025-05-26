package com.example.uni_student.models;

public class Student {
    private String Subject_name, AEM, email;

    public Student(String Subject_name, String AEM, String email) {
        this.Subject_name = Subject_name;
        this.AEM = AEM;
        this.email = email;
    }

    public String getSAEM() {
        return AEM;
    }
    public String getSEmail() {
        return email;
    }
    public String getSubject_name() {
        return Subject_name;
    }
}