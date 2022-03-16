package com.example.spsproject;

public class JobDescriptionList {
    String id, description, company;

    public JobDescriptionList(String id, String description, String company) {
        this.id = id;
        this.description = description;
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }
}

