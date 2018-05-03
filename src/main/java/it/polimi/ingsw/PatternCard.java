package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.*;

public class PatternCard extends Card {
    private int difficulty;
    private Cell[][] patternCard = new Cell[4][5];

    public PatternCard(String name, int ID) {      //temporaneo fino a che non creiamo le effettive pattercard del gioco
        super(name, ID);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                patternCard[i][j] = new Cell();
            }
        }
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) throws NotValidInputException{
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
        string = string.concat(" ID: " + getID() + "\n Name: " + getName() + "\n Difficulty: " +getDifficulty()+"\n");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                string = string.concat(patternCard[i][j].toString());
            }
            string = string.concat("\n");
        }
        return string;
    }

    public void dump() {
        System.out.println(toString());
    }
}
