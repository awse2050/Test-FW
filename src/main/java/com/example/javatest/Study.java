package com.example.javatest;

public class Study {

    private int limit;
    private StudyStatus status;

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
}
