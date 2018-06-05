package it.polimi.ingsw.Model.effects;

public enum ChangeFace {
    SEQUENTIAL("sequential"),
    OPPOSITE("opposite"),
    RANDOM("random"),
    CHOOSE("choose");

    private String changeFace;

    ChangeFace(String changeFace) {
        this.changeFace = changeFace;
    }

    public String getChangeFace() {
        return changeFace;
    }
}