package com.example.javatest;

public class Study {

    private String name;

    private int limit;
    private StudyStatus status;

    public Study(String name, int limit) {
        this.name = name;
        this.limit = limit;
    }

    public Study(int limit) {
        if(limit < 10) {
            throw new IllegalStateException("값이 10 이하 입니다.");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return status;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "Study{" +
                "name='" + name + '\'' +
                ", limit=" + limit +
                ", status=" + status +
                '}';
    }
}
