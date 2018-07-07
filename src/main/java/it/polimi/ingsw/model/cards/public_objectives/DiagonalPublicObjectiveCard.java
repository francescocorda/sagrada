package it.polimi.ingsw.model.cards.public_objectives;

import it.polimi.ingsw.model.game.WindowFrame;

import static it.polimi.ingsw.model.cards.patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.model.cards.patterns.PatternCard.ROW;

public class DiagonalPublicObjectiveCard extends PublicObjectiveCard {

    private String restriction;

    /**
     * creates a new {@link DiagonalPublicObjectiveCard} from the given parameters.
     * @param name : the given {@link String} name
     * @param ID : the given {@link Integer} ID
     * @param points : the given {@link Integer} points
     * @param restriction : the given {@link String} restriction
     */
    public DiagonalPublicObjectiveCard(String name, int ID, int points, String restriction) {
        super(name, ID, points);
        this.restriction = restriction;
    }

    /**
     * @param windowFrame : the given {@link WindowFrame}
     * @return the resulting score of the given {@link WindowFrame}
     */
    @Override
    public int countScore(WindowFrame windowFrame) {
        int score = 0;

        for (int i = 1; i <= ROW; i++) {
            for (int j = 1; j <= COLUMN; j++) {
                boolean added = false;
                if (windowFrame.getDice(i, j) != null) {
                    if (i > 1 && j > 1
                            && windowFrame.getDice(i-1, j-1) != null
                            && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i-1, j-1).getColor())) {
                        score++;
                        added = true;
                    }
                    if (!added && i > 1 && j < COLUMN
                            && windowFrame.getDice(i-1, j+1) != null
                            && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i-1, j+1).getColor())) {
                        score++;
                        added = true;
                    }
                    if (!added && i < ROW && j > 1
                            && windowFrame.getDice(i+1, j-1) != null
                            && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i+1, j-1).getColor())) {
                        score++;
                        added = true;
                    }
                    if (!added && i < ROW && j < COLUMN
                            && windowFrame.getDice(i+1, j+1) != null
                            && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i+1, j+1).getColor())) {
                        score++;
                    }
                }
            }
        }
        return score * points;
    }
}
