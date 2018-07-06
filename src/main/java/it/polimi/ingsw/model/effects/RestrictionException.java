package it.polimi.ingsw.model.effects;

public enum RestrictionException {
    COLOR("COLOR"),
    FACE("FACE"),
    POSITION("POSITION");

    private String exception;

    RestrictionException(String exception) {
        this.exception = exception;
    }

    public String getException() {
        return exception;
    }
}