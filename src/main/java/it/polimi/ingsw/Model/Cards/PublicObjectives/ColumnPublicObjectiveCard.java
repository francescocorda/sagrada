package it.polimi.ingsw.Model.Cards.PublicObjectives;

import it.polimi.ingsw.Model.Game.WindowFrame;

import java.util.HashSet;

public class ColumnPublicObjectiveCard extends PublicObjectiveCard {

    String restriction;

    public ColumnPublicObjectiveCard(String name, int ID, int points, String restriction) {
        super(name, ID, points);
        this.restriction = restriction;
    }

    @Override
    public int countScore(WindowFrame windowFrame) {
        int score = 0;

        for (int i = 1; i <= 5; i++) {
            HashSet<String> set = new HashSet<String>();
            for (int j = 1; j <= 4; j++) {
                if(windowFrame.getDice(j, i) != null) {
                    if (restriction.equals("color")) {
                        set.add(windowFrame.getDice(j, i).getColor().escape());
                    } else if(restriction.equals("face")) {
                        set.add(windowFrame.getDice(j, i).getFace());
                    }
                }
            }
            if (set.size()==4) {
                score += points;
            }
        }
        return score;
    }
}