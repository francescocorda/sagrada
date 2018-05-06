package it.polimi.ingsw;


import it.polimi.ingsw.exceptions.*;

public class WindowFrame {

    private Dice[][] dices = new Dice[4][5];
    private PatternCard patternCard = new PatternCard("EMPTY", 0);

    public void setDice(int row, int col, Dice dice) throws MismatchedRestrictionException,
                                                            InvalidNeighboursException,
                                                            InvalidFirstMoveException,
                                                            OccupiedCellException{

        if(row<1 || row>4 || col<1 || col>5) {
            throw new IndexOutOfBoundsException();
        }

        if(dice==null) {
            throw new NullPointerException();
        }

        if (isEmpty()) {
            if (row == 1 || row == 4 || col == 1 || col == 5) {
                this.dices[row-1][col-1] = dice;
            } else {
                throw new InvalidFirstMoveException();
            }
        } else {

            if (dices[row-1][col-1] != null) { //required cell already occupied
                throw new OccupiedCellException();
            }

            if(patternCard.getExceptionPosition(row, col)) {       //exception on position is actived by a toolcard
                if(patternCard.getRestriction(row, col).equals(Restriction.ANSI_WHITE)
                        || patternCard.getRestriction(row, col).compare(dice.getColor())  //tutti gli equals sono da aggiustare
                        || patternCard.getRestriction(row, col).compare(dice.getFace())) {
                    this.dices[row - 1][col - 1] = dice;
                    patternCard.setExceptionPosition(row,col,false);
                }else {
                    throw new MismatchedRestrictionException();
                }
            }

            else if (hasValidNeighbours(row, col, dice)) {
                if (patternCard.getRestriction(row, col).equals(Restriction.ANSI_WHITE)
                        || patternCard.getExceptionRestriction(row,col)
                        || patternCard.getRestriction(row, col).compare(dice.getColor())  //tutti gli equals sono da aggiustare
                        || patternCard.getRestriction(row, col).compare(dice.getFace())
                        ) {
                    this.dices[row - 1][col - 1] = dice;
                    if (patternCard.getExceptionRestriction(row, col)) {
                        patternCard.setExceptionRestriction(row, col, false);
                    }
                } else {
                    throw new MismatchedRestrictionException();
                }
            } else {
                throw new InvalidNeighboursException();
            }

        }
    }


    public boolean isEmpty() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
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

    public boolean hasValidNeighbours(int row, int col, Dice dice) {    //checks that the cell has at least one neighbour and that all the
                                                                        //horizontal and vertical neighbours don't have the same face or color
        boolean hasNeighbours = false;                                  //as the dice

        if(row<1 || row>4 || col<1 || col>5) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = row - 2; i <= row; i++) {
            for (int j = col - 2; j <= col; j++) {

                if (i >= 0 && i <= 3 && j >= 0 && j <= 4) {
                    if (dices[i][j] != null) {
                        if ((i == row - 1 && (j == col - 2 || j == col)) || (j == col - 1 && (i == row - 2 || i == row))) {
                            if (dices[i][j].getColor().equals(dice.getColor()) || dices[i][j].getFace().equals(dice.getFace())) {
                                return false;
                            } else {
                                hasNeighbours = true;
                            }
                        } else if(!(i==row-1 && j==col-1)) {
                            hasNeighbours = true;
                        }
                    }
                }

            }
        }
        return hasNeighbours;
    }

    public Dice removeDice(int row, int col) throws DiceNotFoundException {
        Dice temp;
        if(row<1 || row>4 || col<1 || col>5) {
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

    @Override
    public String toString(){
        //Used Symbols:
        String emptyDiceSymbol =  "\uD83E\uDD76";
        String verticalSeparatorSymbol = "|";
        String horizontalSeparator = "--"+verticalSeparatorSymbol+"-----------------\t"+verticalSeparatorSymbol+"\t--"+verticalSeparatorSymbol+"-----------------";
        String horizontalCoordinates = "  1  2  3  4  5  ";

        String string="";
        string=string.concat(" Pattern Card:\n -Name: "+patternCard.getName()+"\n -Difficulty: "+patternCard.getDifficulty()+"\n");
        string=string.concat("  "+verticalSeparatorSymbol+horizontalCoordinates+"\t"+verticalSeparatorSymbol+
                "\t  "+verticalSeparatorSymbol + horizontalCoordinates+"\n" + horizontalSeparator + "\n");
        for(int i =0; i<4; i++){
            string=string.concat((i+1)+" " + verticalSeparatorSymbol);
            for (int j = 0; j < 5; j++) {
                String escape = patternCard.getRestriction(i+1, j+1).escape();
                int face = escape.compareTo("\u2680") + 1;
                if (face > 0) {
                    string=string.concat(Restriction.ANSI_WHITE.escape() + "[" + escape + "]" + Restriction.RESET);
                } else {
                    string=string.concat(escape + "[" + emptyDiceSymbol + "]" + Restriction.RESET);
                }
            }
            string=string.concat("\t\t"+verticalSeparatorSymbol+"\t");
            string=string.concat((i+1)+" "+verticalSeparatorSymbol);
            for (int j = 0; j < 5; j++) {
                string=string.concat(dices[i][j]==null ? "[" + emptyDiceSymbol + "]" : dices[i][j].toString());
            }
            string=string.concat("\n");
        }


        return string;
    }

    public void dump(){
        System.out.println(toString());
    }
}