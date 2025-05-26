package com.example.uni_student.models;

import java.util.List;

public class CoursesResponse {

    private boolean error;
    private List<Course> courses;

    public CoursesResponse(boolean error, List<Course> courses) {
        this.error = error;
        this.courses = courses;
    }

    public boolean isError() {
        return error;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
