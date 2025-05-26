package com.example.uni_student.models;

public class User {

    private int id, FK1_University_code;
    private String email, name , AEM, university;

    public User(int id, String email, String name, String AEM, String university, int FK1_University_code) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.AEM = AEM;
        this.university = university;
        this.FK1_University_code = FK1_University_code;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getFK1_University_code() {
        return FK1_University_code;
    }

    public String getName() {
        return name;
    }

    public String getAEM() {
        return AEM;
    }

    public String getUniversity() {
        return university;
    }
}
