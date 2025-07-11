package com.kloudly.classes;

import java.util.ArrayList;
import java.util.List;

public class Student {
    String name;
    int age;
    List<Course> courses;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        this.courses = new ArrayList<>();
    }

    void addCourse(String title) {
        Course course = new Course(title);
        courses.add(course);
    }
}

class Course {
    String title;

    Course(String title) {
        this.title = title;
    }
}