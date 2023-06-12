package com.example.speedtype;

public class TextComparison {
    private String baseText;
    private StringBuilder writtenText = new StringBuilder();
    private int index = -1;

    TextComparison(String baseText) {
        this.baseText = baseText;
    }

    public String getBaseText() {
        return baseText;
    }

    public void addToWritten(String c) {
        writtenText.append(c);
    }

    public void removeFromWritten() {
        writtenText.deleteCharAt(writtenText.length() - 1);
    }

    public StringBuilder getWrittenText() {
        return writtenText;
    }

    public int getIndex() {
        return index;
    }

    public void addToIndex() {
        this.index++;
    }
    public void removeFromIndex(){
        this.index--;
    }
}
