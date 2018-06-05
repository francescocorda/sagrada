package it.polimi.ingsw.Model.Cards.PublicObjectives;

import it.polimi.ingsw.Model.Game.WindowFrame;

import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.ROW;

public class DiagonalPublicObjectiveCard extends PublicObjectiveCard {

    String restriction;

    public DiagonalPublicObjectiveCard(String name, int ID, int points, String restriction) {
        super(name, ID, points);
        this.restriction = restriction;
    }

    public DiagonalPublicObjectiveCard(){}

    @Override
    public int countScore(WindowFrame windowFrame) {
        int score = 0;

        for (int i = 1; i <= ROW; i++) {
            for (int j = 1; j <= COLUMN; j++) {
                if(windowFrame.getDice(i, j) != null) {
                    if(i>1 && j>1 && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i-1, j-1).getColor())) {
                        score++;
                    }
                    if(i>1 && j<COLUMN && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i-1, j+1).getColor())) {
                        score++;
                    }
                    if(i<ROW && j>1 && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i+1, j-1).getColor())) {
                        score++;
                    }
                    if(i<ROW && j<COLUMN && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i+1, j+1).getColor())) {
                        score++;
                    }
                }
            }
        }
        return  score*points;
    }
}
