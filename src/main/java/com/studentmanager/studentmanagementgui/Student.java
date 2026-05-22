package com.studentmanager.studentmanagementgui;

public class Student {
    // 1. The Attributes (Private for security)
    private String studentId;
    private String name;
    private String program;
    private int yearLevel;

    // 2. The Constructor (How we build a new student)
    public Student(String studentId, String name, String program, int yearLevel) {
        this.studentId = studentId;
        this.name = name;
        this.program = program;
        this.yearLevel = yearLevel;
    }

    // 3. Getters (How we read the data later)
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getProgram() { return program; }
    public int getYearLevel() { return yearLevel; }

    // 4. A clean way to print the student's info
    @Override
    public String toString() {
        return "[" + studentId + "] " + name + " | " + program + " - Year " + yearLevel;
    }
}