package it.polimi.ingsw.model.cards.private_objectives;

import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.game.WindowFrame;

import static it.polimi.ingsw.model.cards.patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.model.cards.patterns.PatternCard.ROW;

public class PrivateObjectiveCard extends ObjectiveCard {

    private Color color;

    /**
     * creates a new {@link PrivateObjectiveCard} from the given parameters
     * {@link String} name, {@link Integer} ID, {@link Color} color.
     * @param name : the given {@link String} name
     * @param ID : the given {@link Integer} ID
     * @param color : the given {@link Color} color
     */
    public PrivateObjectiveCard(String name, int ID, Color color) {
        super(name, ID);
        this.color = color;
    }

    /**
     * sets {@link #color} as the given {@link Color} color.
     * @param color : the given {@link Color} color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return {@link #color}.
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param windowFrame : the given {@link WindowFrame} windowFrame
     * @return a {@link Integer} score given the {@link WindowFrame} windowFrame
     */
    @Override
    public int countScore(WindowFrame windowFrame) {

        int score = 0;

        for (int i=1; i<=ROW; i++) {
            for (int j=1; j<=COLUMN; j++) {
                if (windowFrame.getDice(i,j) != null && windowFrame.getDice(i,j).getColor().equals(color)) {
                    score += windowFrame.getDice(i,j).valueOf();
                }
            }

        }
        return score;
    }

    /**
     * @return the {@link String} representation of this {@link PrivateObjectiveCard}.
     */
    @Override
    public String toString() {
        return "ID: " + getID() + "\nName: " + color.escape()+ getName() + Color.RESET + "\nDescription: " + getDescription()+"\n";
    }

    /**
     * displays {@link #toString()}.
     */
    @Override
    public void dump() {
        System.out.println(toString());
    }

}
