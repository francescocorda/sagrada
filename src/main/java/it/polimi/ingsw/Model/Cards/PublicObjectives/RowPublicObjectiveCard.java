package it.polimi.ingsw.Model.Cards.PublicObjectives;



import it.polimi.ingsw.Model.Game.WindowFrame;

import java.util.HashSet;

import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.ROW;

public class RowPublicObjectiveCard extends PublicObjectiveCard {

    String restriction;

    public RowPublicObjectiveCard(String name, int ID, int points, String restriction) {
        super(name, ID, points);
        this.points = points;
        this.restriction = restriction;
    }public RowPublicObjectiveCard(){}



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
