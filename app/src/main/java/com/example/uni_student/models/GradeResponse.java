package com.example.uni_student.models;

import java.util.List;

public class GradeResponse {
    private boolean error;
    private List<Grade> grades;

    public GradeResponse(boolean error, List<Grade> grades) {
        this.error = error;
        this.grades = grades;
    }

    public boolean isError() {
        return error;
    }

    public List<Grade> getGrades() {
        return grades;
    }
}
