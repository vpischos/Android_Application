package com.example.uni_student.models;
import java.util.List;

public class StudentResponse {

    private boolean error;
    private List<Student> students;

    public StudentResponse(boolean error, List<Student> students) {
        this.error = error;
        this.students = students;
    }

    public boolean isError() {
        return error;
    }

    public List<Student> getStudents() {
        return students;
    }
}