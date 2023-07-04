package com.example.speedtype;

import java.time.LocalDate;


public class ResultStructure {
    private int index;
    private String name;
    private int wpm;
    private LocalDate date;

    ResultStructure(int index, String name, int wpm, LocalDate date) {
        this.index = index;
        this.name = name;
        this.wpm = wpm;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getWpm() {
        return wpm;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
