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

    /**
     * Function checks whether last symbol in users written text is equal to a symbol at the same index on given base text
     * @return -1 if it is not the same and the index when they are the same
     */
    public int sameSymbolCheck() {
        int writtenLast = writtenText.length() - 1;
        return writtenText.charAt(writtenLast) == baseText.charAt(writtenLast) ? writtenLast : -1;
    }
}
