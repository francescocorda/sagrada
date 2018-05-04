package it.polimi.ingsw;

public abstract class ObjectiveCard extends Card {

    private String description;

    public ObjectiveCard(String name, int ID) {
        super(name, ID);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract int countScore(WindowFrame windowFrame);

}
