package it.polimi.ingsw.Model.effects;

public enum Element {
    DRAFTPOOL("draftpool"),
    WINDOW("window"),
    ROUNDTRACK("roundtrack"),
    DICEBAG("dicebag");

    private String element;

    Element(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}