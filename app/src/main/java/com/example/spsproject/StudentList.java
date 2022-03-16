package com.example.spsproject;

public class StudentList {
    String id, name, email, branch, percentage;

    public StudentList(String id, String name, String email, String branch, String percentage) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.branch = branch;
        this.percentage = percentage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBranch() {
        return branch;
    }

    public String getPercentage() {
        return percentage;
    }
}
