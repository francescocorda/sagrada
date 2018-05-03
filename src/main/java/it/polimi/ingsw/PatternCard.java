package it.polimi.ingsw;

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

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public Restriction getRestriction(int row, int col) {
        return patternCard[row - 1][col - 1].getRestriction();
    }

    public void setRestriction(int row, int col, Restriction restriction) {
        this.patternCard[row - 1][col - 1].setRestriction(restriction);
    }

    public boolean getExceptionRestriction(int row, int col) {
        return patternCard[row - 1][col - 1].getExceptionRestriction();
    }

    public void setExceptionRestriction(int row, int col, boolean exception) {
        this.patternCard[row - 1][col - 1].setExceptionRestriction(exception);
    }

    public boolean getExceptionPosition(int row, int col) {

        return patternCard[row - 1][col - 1].getExceptionPosition();
    }

    public void setExceptionPosition(int row, int col, boolean exception) {
        this.patternCard[row - 1][col - 1].setExceptionPosition(exception);
    }

    @Override
    public String toString() {
        String string = "";
        string=string.concat(" ID: " + getID() + "\n Name: " + getName() + "\n Difficulty: " +getDifficulty()+"\n");
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 5; j++) {
                String escape = getRestriction(i, j).escape();
                int face = escape.compareTo("\u2680") + 1;
                if (face > 0) {
                    string=string.concat(Restriction.ANSI_WHITE.escape() + "[" + escape + "]" + Restriction.RESET);
                } else {
                    string=string.concat(escape + "[" + "\u25A0" + "]" + Restriction.RESET);
                }
            }
            string=string.concat("\n");
        }
        return string;
    }

    public void dump() {
        System.out.println(toString());
    }
}
