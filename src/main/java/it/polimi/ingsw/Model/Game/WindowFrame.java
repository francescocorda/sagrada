package it.polimi.ingsw.Model.Game;


import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.rmi.RemoteException;

import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.ROW;

public class WindowFrame implements Serializable {


    private Dice[][] dices;
    private PatternCard patternCard;

    public WindowFrame(){
        dices = new Dice[ROW][COLUMN];
        patternCard=null;
    }

    public void setDice(int row, int col, Dice dice) throws MismatchedRestrictionException,
            InvalidNeighboursException,
            InvalidFirstMoveException,
            OccupiedCellException{

        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            patternCard.setFalseExceptions();
            throw new IndexOutOfBoundsException();
        }

        if(dice==null) {
            patternCard.setFalseExceptions();
            throw new NullPointerException();
        }

        if (isEmpty()) {
            if (row == 1 || row == ROW || col == 1 || col == COLUMN) {
                if(this.patternCard.getRestriction(row, col).equals(Restriction.ANSI_WHITE)
                        ||this.patternCard.getRestriction(row, col).compare(dice.getColor())
                        ||this.patternCard.getRestriction(row, col).compare(dice.getFace())
                        ||this.patternCard.getExceptionRestriction(row, col)){
                    this.dices[row-1][col-1] = dice;
                } else {
                    patternCard.setFalseExceptions();
                    throw new InvalidFirstMoveException();
                }
            } else {
                patternCard.setFalseExceptions();
                throw new InvalidFirstMoveException();
            }
        } else {   //if is not the first move

            if (dices[row-1][col-1] != null) { //required cell already occupied
                patternCard.setFalseExceptions();
                throw new OccupiedCellException();
            }

            if(patternCard.getExceptionPosition(row, col) && checkNeighboursRestriction(row, col, dice)) {       //exception on position is actived by a toolcard
                if(patternCard.getRestriction(row, col).equals(Restriction.ANSI_WHITE)
                        || patternCard.getExceptionRestriction(row,col)
                        || patternCard.getRestriction(row, col).compare(dice.getColor())
                        || patternCard.getRestriction(row, col).compare(dice.getFace())) {
                    this.dices[row - 1][col - 1] = dice;
                    patternCard.setFalseExceptions();
                }else {
                    patternCard.setFalseExceptions();
                    throw new MismatchedRestrictionException();
                }
            }

            else if (hasNeighbours(row, col) && checkNeighboursRestriction(row, col, dice)) {
                if (patternCard.getRestriction(row, col).equals(Restriction.ANSI_WHITE)
                        || patternCard.getExceptionRestriction(row,col)
                        || patternCard.getRestriction(row, col).compare(dice.getColor())
                        || patternCard.getRestriction(row, col).compare(dice.getFace())
                        ) {
                    this.dices[row - 1][col - 1] = dice;
                    patternCard.setFalseExceptions();
                } else {
                    patternCard.setFalseExceptions();
                    throw new MismatchedRestrictionException();
                }
            } else {
                patternCard.setFalseExceptions();
                throw new InvalidNeighboursException();
            }

        }
    }


    public boolean isEmpty() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (dices[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public Dice getDice(int row, int col) {
        return dices[row-1][col-1];
    }

    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    public PatternCard getPatternCard() {
        return this.patternCard;
    }

    public boolean checkNeighboursRestriction(int row, int col, Dice dice) {    //checks that all the horizontal and vertical neighbours
        // don't have the same face or color as the dice

        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = row - 2; i <= row; i++) {
            for (int j = col - 2; j <= col; j++) {

                if (i >= 0 && i <= 3 && j >= 0 && j <= ROW) {
                    if (dices[i][j] != null) {
                        if ((i == row - 1 && (j == col - 2 || j == col)) || (j == col - 1 && (i == row - 2 || i == row))) {
                            if (dices[i][j].getColor().equals(dice.getColor()) || dices[i][j].getFace().equals(dice.getFace())) {
                                return false;
                            }
                        }
                    }
                }

            }
        }
        return true;
    }

    public boolean hasNeighbours(int row, int col) {    //checks that the cell has at least one neighbour

        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = row - 2; i <= row; i++) {
            for (int j = col - 2; j <= col; j++) {

                if (i >= 0 && i <= 3 && j >= 0 && j <= ROW) {
                    if (dices[i][j] != null && !(i==row-1 && j==col-1)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }


    public Dice removeDice(int row, int col) throws DiceNotFoundException {
        Dice temp;
        if(row<1 || row>ROW || col<1 || col>COLUMN) {
            throw new IndexOutOfBoundsException();
        }

        if(dices[row - 1][col - 1]!=null) {
            temp = dices[row - 1][col - 1];
            dices[row - 1][col - 1] = null;
            return temp;
        } else {
            throw new DiceNotFoundException();
        }
    }

    public void enableRestriction(String restrictionToIgnore){
        for(int i=1; i<=ROW; i++){
            for(int j=1; j<=COLUMN; j++){
                if(restrictionToIgnore.compareTo("VALUE")==0){
                    if(patternCard.getRestriction(i,j).escape().compareTo(Restriction.ONE.escape())>=0){
                        patternCard.setExceptionRestriction(i, j, true);
                    }
                }else if(restrictionToIgnore.compareTo("COLOR")==0){
                    if(patternCard.getRestriction(i,j).escape().compareTo(Restriction.ONE.escape())<0){
                        patternCard.setExceptionRestriction(i, j, true);
                    }
                }else if(restrictionToIgnore.compareTo("POSITION")==0){
                    patternCard.setExceptionPosition(i, j, true);
                }
            }
        }
    }

    public void update() throws RemoteException {

    }

    @Override
    public String toString(){
        //Used Symbols:
        String emptyDiceSymbol =  "\uD83E\uDD76";
        String verticalSeparatorSymbol = "|";
        String horizontalLine = "-----------------\t";
        String horizontalSeparator = "--"+verticalSeparatorSymbol+horizontalLine+
                verticalSeparatorSymbol+"\t--"+verticalSeparatorSymbol+horizontalLine+
                verticalSeparatorSymbol+"\t--"+verticalSeparatorSymbol+horizontalLine;
        String horizontalCoordinates = "  1  2  3  4  5  ";

        String string="";
        string=string.concat(" Pattern Card:\n -Name: "+patternCard.getName()+"\n -Difficulty: "+patternCard.getDifficulty()+"\n");
        string=string.concat("  "+verticalSeparatorSymbol+horizontalCoordinates+"\t"+verticalSeparatorSymbol+
                "\t  "+verticalSeparatorSymbol + horizontalCoordinates+"\t"+verticalSeparatorSymbol+
                "\t  "+verticalSeparatorSymbol + horizontalCoordinates+"\n" + horizontalSeparator + "\n");
        for(int i =0; i<ROW; i++){
            string=string.concat((i+1)+" " + verticalSeparatorSymbol);
            for (int j = 0; j < COLUMN; j++) {
                String escape = patternCard.getRestriction(i+1, j+1).escape();
                int face = escape.compareTo("\u2680") + 1;
                if (face > 0) {
                    string=string.concat(Restriction.ANSI_WHITE.escape() + "[" + escape + "]" + Restriction.RESET);
                } else {
                    string=string.concat(escape + "[" + emptyDiceSymbol + "]" + Restriction.RESET);
                }
            }
            //DICE MATRIX
            string=string.concat("\t\t"+verticalSeparatorSymbol+"\t");
            string=string.concat((i+1)+" "+verticalSeparatorSymbol);
            for (int j = 0; j < COLUMN; j++) {
                string=string.concat(dices[i][j]==null ? "[" + emptyDiceSymbol + "]" : dices[i][j].toString());
            }
            //EXCEPTION MATRIX
            string=string.concat("\t\t"+verticalSeparatorSymbol+"\t");
            string=string.concat((i+1)+" "+verticalSeparatorSymbol+" ");
            for (int j = 0; j < COLUMN; j++) {
                if(patternCard.getExceptionRestriction(i+1, j+1)){
                    string=string.concat(patternCard.getExceptionPosition(i+1, j+1) ? "[b]" : "[r]");
                }else{
                    string=string.concat(patternCard.getExceptionPosition(i+1, j+1) ? "[p]" : "[n]");
                }
            }
            string=string.concat("\n");
        }
        string=string.concat("\n r: exception restriction\t p: exception position\t b: both\t n: no exception\n");
        return string;
    }


    public String toGameString(){
        //Used Symbols:
        String emptyDiceSymbol =  "\uD83E\uDD76";
        String verticalSeparatorSymbol = "|";
        String horizontalLine = "-----------------\t";
        String horizontalSeparator = "--"+verticalSeparatorSymbol+horizontalLine;
        String horizontalCoordinates = "  1  2  3  4  5  ";

        String string="";
        string=string.concat(" Pattern Card:\n -Name: "+patternCard.getName()+"\n -Difficulty: "+patternCard.getDifficulty()+"\n");
        string=string.concat("  "+verticalSeparatorSymbol+horizontalCoordinates+"\n" + horizontalSeparator + "\n");
        for(int i =0; i<ROW; i++){
            string=string.concat((i+1)+" " + verticalSeparatorSymbol);
            for (int j = 0; j < COLUMN; j++) {
                if(dices[i][j] == null){
                    String escape = patternCard.getRestriction(i+1, j+1).escape();
                    int face = escape.compareTo("\u2680") + 1;
                    if (face > 0) {
                        string=string.concat(Restriction.ANSI_WHITE.escape() + "[" + escape + "]" + Restriction.RESET);
                    } else {
                        string=string.concat(escape + "[" + emptyDiceSymbol + "]" + Restriction.RESET);
                    }
                }
                else
                    string = string.concat(dices[i][j].toString());
            }
            string=string.concat("\n");
        }
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }

}