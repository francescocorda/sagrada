package it.polimi.ingsw.model.cards.patterns;

import java.io.Serializable;

public class Cell implements Serializable {
    private Restriction restriction;
    private boolean exceptionRestriction;
    private boolean exceptionPosition;

    /**
     * creates a new {@link Cell}
     */
    public Cell() {
        this.restriction = Restriction.WHITE;
        this.exceptionRestriction = false;
        this.exceptionPosition = false;
    }

    /**
     * creates a new {@link Cell} from the given {@link Cell}.
     * @param cell : the given {@link Cell}
     */
    public Cell(Cell cell) {
        this.restriction = cell.getRestriction();
        this.exceptionRestriction = cell.getExceptionRestriction();
        this.exceptionPosition = cell.getExceptionPosition();
    }

    /**
     * sets {@link #restriction} as the given {@link Restriction} restriction.
     * @param restriction : the given {@link Restriction} restriction
     */
    public void setRestriction(Restriction restriction) {
        this.restriction = restriction;
    }

    /**
     * @return {@link #restriction}.
     */
    public Restriction getRestriction() {
        return restriction;
    }

    /**
     * sets the {@link #exceptionRestriction} as the given {@link Boolean} exception.
     * @param exception : the given {@link Boolean} exception
     */
    public void setExceptionRestriction(boolean exception) {
        this.exceptionRestriction = exception;
    }

    /**
     * @return {@link #exceptionRestriction}
     */
    public boolean getExceptionRestriction() {
        return exceptionRestriction;
    }

    /**
     * sets the {@link #exceptionPosition} as the given {@link boolean} exception.
     * @param exception : the given {@link boolean} exception
     */
    public void setExceptionPosition(boolean exception) {
        this.exceptionPosition = exception;
    }

    /**
     * @return {@link #exceptionPosition}
     */
    public boolean getExceptionPosition() {
        return exceptionPosition;
    }

    /**
     * shows {@link Cell#toString()}.
     */
    public void dump() {
        System.out.println(toString());
    }

    /**
     * @return the {@link String} representation of this {@link Cell}
     */
    public String toString() {
        String escape = getRestriction().escape();
        if (escape.compareTo("\u2680") + 1> 0) {
            return Restriction.WHITE.escape() + "[" + escape + "]" + Restriction.RESET;
        } else {
            return escape + "[" + "\u25FB" + "]" + Restriction.RESET;
        }
    }
}