package it.polimi.ingsw.model.cards.public_objectives;

import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.game.WindowFrame;

public class PublicObjectiveCard extends ObjectiveCard {

    int points;

    /**
     * creates a new {@link PublicObjectiveCard} from the given parameters.
     * @param name : the given {@link String} name
     * @param ID : the given {@link Integer} ID
     * @param points : the given {@link Integer} points
     */
    public PublicObjectiveCard(String name, int ID, int points) {
        super(name, ID);
        this.points = points;
    }

    /**
     * creates a new {@link PublicObjectiveCard}.
     */
    public PublicObjectiveCard(){}

    /**
     * @param windowFrame : the given {@link WindowFrame}
     * @return the resulting score of the given {@link WindowFrame}
     */
    @Override
    public int countScore(WindowFrame windowFrame) {
        return 0;
    }

    /**
     * @return {@link PublicObjectiveCard#points}
     */
    public int getPoints() {
        return points;
    }


    /**
     * @return a {@link String} representation cf this {@link PublicObjectiveCard}
     */
    @Override
    public String toString() {
        String string = super.toString();
        string=string.concat("\nPoints: " + points);
        return string;
    }

    /**
     * displays {@link #toString()}.
     */
    @Override
    public void dump() {
        System.out.println(toString());
    }

}
