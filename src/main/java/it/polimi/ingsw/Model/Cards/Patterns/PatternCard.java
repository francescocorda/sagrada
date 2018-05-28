package it.polimi.ingsw.Model.Cards.Patterns;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.exceptions.NotValidInputException;


public class PatternCard extends Card {
    private int difficulty;
    private Cell[][] patternCard;

    public PatternCard(String name, int ID) {
        super(name, ID);
        patternCard = new Cell[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                patternCard[i][j] = new Cell();
            }
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) throws NotValidInputException {
        if(difficulty<3||difficulty>6) throw new NotValidInputException();
        this.difficulty = difficulty;
    }

    public Restriction getRestriction(int row, int col) {
        return patternCard[row - 1][col - 1].getRestriction();
    }

    public void setRestriction(int row, int col, Restriction restriction) {
        if(row<1 || row>4 || col<1 || col>5) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setRestriction(restriction);
    }

    public boolean getExceptionRestriction(int row, int col) {
        return patternCard[row - 1][col - 1].getExceptionRestriction();
    }

    public void setExceptionRestriction(int row, int col, boolean exception) {
        if(row<1 || row>4 || col<1 || col>5) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setExceptionRestriction(exception);
    }

    public boolean getExceptionPosition(int row, int col) {

        return patternCard[row - 1][col - 1].getExceptionPosition();
    }

    public void setExceptionPosition(int row, int col, boolean exception) {
        if(row<1 || row>4 || col<1 || col>5) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setExceptionPosition(exception);
    }

    @Override
    public String toString() {
        String string = "";
        string = string.concat(super.toString()+"\nDifficulty: " +getDifficulty()+"\n");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                string = string.concat(patternCard[i][j].toString());
            }
            string = string.concat("\n");
        }
        return string;
    }

    @Override
    public void dump() {
        System.out.println(toString());
    }

    public void setFalseExceptions(){
        for(int i=1; i<=4; i++){
            for(int j=1; j<=5; j++){
                this.setExceptionPosition(i,j, false);
                this.setExceptionRestriction(i,j, false);
            }
        }
    }
}
