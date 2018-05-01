package it.polimi.ingsw;

public class Cell {
    private Restriction restriction;
    private boolean exception;


    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }
    public Restriction getRestriction() {
        return restriction;
    }

    public void setException(boolean exception) {
        this.exception = exception;
    }
    public boolean getException() {
        return exception;
    }

}
