package it.polimi.ingsw.model.effects;

public enum Element {
    DRAFTPOOL("DRAFTPOOL"),
    WINDOW("WINDOW"),
    ROUNDTRACK("ROUNDTRACK"),
    DICEBAG("DICEBAG");

    private String element;

    Element(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }
}