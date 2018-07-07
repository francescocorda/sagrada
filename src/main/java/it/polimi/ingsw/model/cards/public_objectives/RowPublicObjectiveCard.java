package it.polimi.ingsw.model.cards.public_objectives;



import it.polimi.ingsw.model.game.WindowFrame;

import java.util.HashSet;

import static it.polimi.ingsw.model.cards.patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.model.cards.patterns.PatternCard.ROW;

public class RowPublicObjectiveCard extends PublicObjectiveCard {

    String restriction;

    /**
     * creates a new {@link RowPublicObjectiveCard} from the given parameters.
     * @param name : is the {@link String} name
     * @param ID : is the given int ID
     * @param points : is the given int points
     * @param restriction : is {@link String} restriction
     */
    public RowPublicObjectiveCard(String name, int ID, int points, String restriction) {
        super(name, ID, points);
        this.points = points;
        this.restriction = restriction;
    }public RowPublicObjectiveCard(){}


    /**
     * @param windowFrame : the given {@link WindowFrame} windowFrame
     * @return the score of the given {@link WindowFrame} windowFrame.
     */
    @Override
    public int countScore(WindowFrame windowFrame) {
        int score = 0;

        for (int i = 1; i <= ROW; i++) {
            HashSet<String> set = new HashSet<String>();
            for (int j = 1; j <= COLUMN; j++) {
                if(windowFrame.getDice(i, j) != null) {
                    if (restriction.equals("color")) {
                        set.add(windowFrame.getDice(i, j).getColor().escape());
                    } else if(restriction.equals("face")) {
                        set.add(windowFrame.getDice(i, j).getFace());
                    }
                }
            }
            if (set.size()==5) {
                score += points;
            }
        }
        return score;
    }
}
