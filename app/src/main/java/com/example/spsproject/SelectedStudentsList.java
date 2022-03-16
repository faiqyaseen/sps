package com.example.spsproject;

public class SelectedStudentsList {
    String id, company_name, company_location, job_description, student_name, student_branch, student_percentage, student_email;

    public SelectedStudentsList(
            String id,
            String company_name,
            String company_location,
            String job_description,
            String student_name,
            String student_branch,
            String student_percentage,
            String student_email
    ) {
        this.id = id;
        this.company_name = company_name;
        this.company_location = company_location;
        this.job_description = job_description;
        this.student_name = student_name;
        this.student_branch = student_branch;
        this.student_percentage = student_percentage;
        this.student_email = student_email;
    }

    public String getId() {
        return id;
    }

    public String getCompanyName() {
        return company_name;
    }

    public String getCompanyLocation() {
        return company_location;
    }

    public String getJobDescription() {
        return job_description;
    }

    public String getStudentName() {
        return student_name;
    }

    public String getStudentBranch() {
        return student_branch;
    }

    public String getStudentPercentage() {
        return student_percentage;
    }

    public String getStudentEmail() {
        return student_email;
    }
}
