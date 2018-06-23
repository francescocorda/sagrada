package it.polimi.ingsw.Model.Cards.Patterns;

import it.polimi.ingsw.Model.Cards.Card;
import it.polimi.ingsw.exceptions.NotValidInputException;

public class PatternCard extends Card {

    public static final int ROW = 4;
    public static final int COLUMN = 5;
    private int difficulty;
    private Cell[][] patternCard;

    public PatternCard(String name, int ID) {
        super(name,ID);
        patternCard = new Cell[ROW][COLUMN];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                patternCard[i][j] = new Cell();
            }
        }
    }

    public PatternCard(PatternCard patternCard) {
        super(patternCard.getName(), patternCard.getID());
        this.difficulty = patternCard.getDifficulty();
        this.patternCard = new Cell[ROW][COLUMN];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                this.patternCard[i][j] = new Cell(patternCard.getCell(i,j));
            }
        }
    }

    public Cell getCell (int row, int col) {
        return patternCard[row][col];
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
        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setRestriction(restriction);
    }

    public boolean getExceptionRestriction(int row, int col) {
        return patternCard[row - 1][col - 1].getExceptionRestriction();
    }

    public void setExceptionRestriction(int row, int col, boolean exception) {
        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setExceptionRestriction(exception);
    }

    public boolean getExceptionPosition(int row, int col) {
        return patternCard[row - 1][col - 1].getExceptionPosition();
    }

    public void setExceptionPosition(int row, int col, boolean exception) {
        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setExceptionPosition(exception);
    }

    @Override
    public String toString() {
        String string = "";
        string = string.concat(super.toString()+"\nDifficulty: " +getDifficulty()+"\n");
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
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

    public void disableExceptions(){
        for(int i=1; i<=ROW; i++){
            for(int j=1; j<=COLUMN; j++){
                this.setExceptionPosition(i,j, false);
                this.setExceptionRestriction(i,j, false);
            }
        }
    }
}
