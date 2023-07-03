package com.example.speedtype;

import java.time.LocalDate;


public class ResultStructure {
    private String name;
    private String text;
    private int wpm;
    private LocalDate date;

    ResultStructure(String name, String text, int wpm, LocalDate date) {
        this.name = name;
        this.text = text;
        this.wpm = wpm;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public int getWpm() {
        return wpm;
    }

    public LocalDate getDate() {
        return date;
    }

}
