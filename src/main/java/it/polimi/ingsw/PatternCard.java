package it.polimi.ingsw;

public class PatternCard {
    private int difficulty;
    private Cell[][] patternCard = new Cell[4][5];

    public int getDifficulty() {
     return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Restriction getRestriction(int row, int col) {
         return patternCard[row][col].getRestriction();
    }

    public void setRestriction(int row, int col, Restriction restriction) {
        this.patternCard[row][col].setRestriction(restriction);
    }

    public boolean getExcepion(int row, int col) {
        return patternCard[row][col].getException();
    }

    public void setExcepion(int row, int col, boolean exception) {
        this.patternCard[row][col].setException(exception);
    }
}
