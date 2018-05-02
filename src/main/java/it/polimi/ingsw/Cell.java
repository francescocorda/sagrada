package it.polimi.ingsw;

public class Cell {
    private Restriction restriction;
    private boolean exceptionRestriction;
    private boolean exceptionPosition;

    public Cell() {
        this.restriction = Restriction.ANSI_WHITE;
        this.exceptionRestriction = false;
        this.exceptionPosition = false;
    }

    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }
    public Restriction getRestriction() {
        return restriction;
    }

    public void setExceptionRestriction(boolean exception) {
        this.exceptionRestriction = exception;
    }
    public boolean getExceptionRestriction() {
        return exceptionRestriction;
    }

    public void setExceptionPosition(boolean exception) {
        this.exceptionPosition = exception;
    }
    public boolean getExceptionPosition() {
        return exceptionPosition;
    }
}
