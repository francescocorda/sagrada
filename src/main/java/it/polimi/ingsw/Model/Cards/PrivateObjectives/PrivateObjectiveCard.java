package it.polimi.ingsw.Model.Cards.PrivateObjectives;

import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.Model.Cards.ObjectiveCard;
import it.polimi.ingsw.Model.Game.WindowFrame;

import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.ROW;

public class PrivateObjectiveCard extends ObjectiveCard {

    Color color;

    public PrivateObjectiveCard(String name, int ID, Color color) {
        super(name, ID);
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

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

    @Override
    public String toString() {
        return "ID: " + getID() + "\nName: " + color.escape()+ getName() + Color.RESET + "\nDescription: " + getDescription()+"\n";
    }

    @Override
    public void dump() {
        System.out.println(toString());
    }

}
