package it.polimi.ingsw;

public class PatternCard {
    private int difficulty;
    private Cell[][] patternCard = new Cell[4][5];

    public PatternCard() {      //temporaneo fino a che non creiamo le effettive pattercard del gioco
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                patternCard[i][j] = new Cell();
            }
        }
    }


    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Restriction getRestriction(int row, int col) {
        return patternCard[row-1][col-1].getRestriction();
    }

    public void setRestriction(int row, int col, Restriction restriction) {
        this.patternCard[row-1][col-1].setRestriction(restriction);
    }

    public boolean getExceptionRestriction(int row, int col) {
        return patternCard[row-1][col-1].getExceptionRestriction();
    }

    public void setExceptionRestriction(int row, int col, boolean exception) {
        this.patternCard[row-1][col-1].setExceptionRestriction(exception);
    }

    public boolean getExceptionPosition(int row, int col) {

        return patternCard[row-1][col-1].getExceptionPosition();
    }

    public void setExceptionPosition(int row, int col, boolean exception) {
        this.patternCard[row-1][col-1].setExceptionPosition(exception);
    }
}
