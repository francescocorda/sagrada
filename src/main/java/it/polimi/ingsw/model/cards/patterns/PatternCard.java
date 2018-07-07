package it.polimi.ingsw.model.cards.patterns;

import it.polimi.ingsw.model.cards.Card;
import it.polimi.ingsw.exceptions.NotValidInputException;

public class PatternCard extends Card {

    public static final int ROW = 4;
    public static final int COLUMN = 5;
    private int difficulty;
    private Cell[][] patternCard;

    /**
     * creates a new {@link PatternCard}.
     */
    public PatternCard() {
        new PatternCard(null, 0);
    }

    /**
     * creates a new {@link PatternCard} given the {@link String} name and the {@link Integer} ID.
     * @param name : the given {@link String} name
     * @param ID : the given {@link Integer} ID
     */
    public PatternCard(String name, int ID) {
        super(name,ID);
        patternCard = new Cell[ROW][COLUMN];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                patternCard[i][j] = new Cell();
            }
        }
    }

    /**
     * creates a new {@link PatternCard} given a {@link PatternCard} patternCard.
     * @param patternCard : creates a new {@link PatternCard} given a {@link PatternCard} patternCard
     */
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

    /**
     * @param row : the given {@link Integer} row
     * @param col : the given {@link Integer} column
     * @return the {@link Cell} corresponding to the given coordinates.
     */
    public Cell getCell (int row, int col) {
        return patternCard[row][col];
    }

    /**
     * @return {@link #difficulty}.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * sets the {@link #difficulty} as the given {@link Integer} difficulty.
     * @param difficulty : the given {@link Integer} difficulty
     * @throws NotValidInputException if the given difficulty value is not correct
     */
    public void setDifficulty(int difficulty) throws NotValidInputException {
        if(difficulty<0) throw new NotValidInputException();
        this.difficulty = difficulty;
    }

    /**
     * @param row : the given {@link Integer} row
     * @param col : the given {@link Integer} column
     * @return the {@link Cell#restriction}
     */
    public Restriction getRestriction(int row, int col) {
        return patternCard[row - 1][col - 1].getRestriction();
    }

    /**
     * sets the {@link Cell#restriction} of the given coordinates as the given {@link Integer} restriction.
     * @param row : the given {@link Integer} row
     * @param col : the given {@link Integer} column
     * @param restriction : the given {@link Integer} restriction
     */
    public void setRestriction(int row, int col, Restriction restriction) {
        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setRestriction(restriction);
    }

    /**
     * @param row : the given {@link Integer} row
     * @param col : the given {@link Integer} column
     * @return the {@link Cell#exceptionRestriction} at the given coordinates
     */
    public boolean getExceptionRestriction(int row, int col) {
        return patternCard[row - 1][col - 1].getExceptionRestriction();
    }

    /**
     * sets the {@link Cell#exceptionRestriction} at the given coordinates as the given {@link Boolean} exception.
     * @param row : the given {@link Integer} row
     * @param col : the given {@link Integer} column
     * @param exception : the given {@link Boolean} exception
     */
    public void setExceptionRestriction(int row, int col, boolean exception) {
        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setExceptionRestriction(exception);
    }

    /**
     * @param row : the given {@link Integer} row
     * @param col : the given {@link Integer} column
     * @return the {@link Cell#exceptionPosition} at the given coordinates
     */
    public boolean getExceptionPosition(int row, int col) {
        return patternCard[row - 1][col - 1].getExceptionPosition();
    }

    /**
     * sets the {@link Cell#exceptionPosition} at the given coordinates as the given {@link Boolean} exception.
     * @param row : the given {@link Integer} row
     * @param col : the given {@link Integer} column
     * @param exception : the given {@link Boolean} exception
     */
    public void setExceptionPosition(int row, int col, boolean exception) {
        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }
        this.patternCard[row - 1][col - 1].setExceptionPosition(exception);
    }

    /**
     * @return the {@link String} representation of this {@link PatternCard}
     */
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

    /**
     * displays the {@link PatternCard#toString()}.
     */
    @Override
    public void dump() {
        System.out.println(toString());
    }

    /**
     * disables all the {@link PatternCard} exceptions.
     */
    public void disableExceptions(){
        for(int i=1; i<=ROW; i++){
            for(int j=1; j<=COLUMN; j++){
                this.setExceptionPosition(i,j, false);
                this.setExceptionRestriction(i,j, false);
            }
        }
    }
}
