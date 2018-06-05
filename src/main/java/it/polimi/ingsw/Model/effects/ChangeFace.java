package it.polimi.ingsw.Model.effects;

public enum ChangeFace {
    SEQUENTIAL("SEQUENTIAL"),
    OPPOSITE("OPPOSITE"),
    RANDOM("RANDOM"),
    CHOOSE("CHOOSE");

    private String changeFace;

    ChangeFace(String changeFace) {
        this.changeFace = changeFace;
    }

    public String getChangeFace() {
        return changeFace;
    }
}