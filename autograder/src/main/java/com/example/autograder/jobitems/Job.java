package com.example.autograder.jobitems;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Nullable Integer id;

    private String file;

    private String status = "PENDING";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Integer[] getTestcases() {
        return testcases;
    }

    public void setTestcases(Integer[] testcases) {
        this.testcases = testcases;
    }

    Integer[] testcases = new Integer[10];

    public Job(String file) {
        this.file = file;
    }

    protected Job() {}
}
