package com.example.speedtype;

public class TextComparison {

    private final String baseText;
    private StringBuilder writtenText = new StringBuilder();
    private int index = 0;
    private boolean match = true;
    private int[] mistakeIndex = {-1, -1, -1, -1, -1, -1, -1, -1};
    private int nrOfMistakes = -1;
    private int lastCorrect = -1;

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

    public boolean getMatch() {
        return match;
    }

    public int[] getMistakeIndex() {
        return mistakeIndex;
    }

    public int getNrOfMistakes() {
        return nrOfMistakes;
    }

    public int getLastCorrect() {
        return lastCorrect;
    }


    public void addToIndex() {
        this.index++;
    }

    public void removeFromIndex(){
        this.index--;
    }

    public void setMatchTrue(){
    match = true;
    }

    public void setMatchFalse(){
        match = false;
    }


    public void setLastCorrect(int lastCorrect) {
        this.lastCorrect = lastCorrect;
    }

    public void addToMistakeArray(int index) {
        nrOfMistakes += 1;
        mistakeIndex[nrOfMistakes] = index;
    }

    public void removeFromMistakeArray() {
        mistakeIndex[nrOfMistakes] = -1;
        nrOfMistakes--;
    }



    /**
     * Function checks whether last symbol in users written text is equal to a symbol at the same index on given base text
     * @return -1 if it is not the same and the index when they are the same
     */
    public int sameSymbolCheck() {
        int writtenLast = writtenText.length() - 1;
        if (writtenLast == - 1 || writtenText.charAt(writtenLast) == baseText.charAt(writtenLast) || writtenText.charAt(writtenLast) == '\n')
            return -1;
        return writtenLast;
    }
}
