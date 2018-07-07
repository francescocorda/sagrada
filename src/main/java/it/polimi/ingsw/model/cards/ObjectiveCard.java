package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.game.WindowFrame;

public abstract class ObjectiveCard extends Card{

    private String description;

    /**
     * creates a new {@link ObjectiveCard} from the given parameters.
     * @param name : the given {@link String} name
     * @param ID : the given int ID
     */
    public ObjectiveCard(String name, int ID) {
        super(name, ID);
    }

    /**
     * creates a new {@link ObjectiveCard}.
     */
    public ObjectiveCard(){}

    /**
     * sets {@link #description} as the given {@link String} description.
     * @param description : the given {@link String} description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return {@link #description}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param windowFrame : the given {@link WindowFrame} windowFrame
     * @return int score of the given {@link WindowFrame} windowFrame.
     */
    public abstract int countScore(WindowFrame windowFrame);

    /**
     * @return the representation of this {@link ObjectiveCard}.
     */
    @Override
    public String toString(){
        String string = super.toString();
        string=string.concat("\nDescription: "+description);
        return string;
    }

    /**
     * displays {@link #toString()}.
     */
    @Override
    public void dump(){
        System.out.println(toString());
    }

}
