package it.polimi.ingsw.model.cards.public_objectives;

import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.model.game.WindowFrame;

import java.util.ArrayList;

import static it.polimi.ingsw.model.cards.patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.model.cards.patterns.PatternCard.ROW;

public class SetPublicObjectiveCard extends PublicObjectiveCard {

    String restriction;
    ArrayList<String> elements;
    int[] numPerElement;

    public SetPublicObjectiveCard(String name, int ID, int points, String restriction, ArrayList<String> elements) {
        super(name, ID, points);
        this.restriction = restriction;
        this.elements = elements;
        this.numPerElement = new int[elements.size()];
        for (int i = 0; i<numPerElement.length; i++) {
            numPerElement[i] = 0;
        }
    }

    @Override
    public int countScore(WindowFrame windowFrame) {

        for (int i = 1; i <= ROW; i++) {
            for (int j = 1; j <= COLUMN; j++) {
                if(windowFrame.getDice(i, j) != null) {
                    if(restriction.equals("color")){
                        if (elements.contains(windowFrame.getDice(i,j).getColor().escape())) {
                            int position = elements.indexOf(windowFrame.getDice(i,j).getColor().escape());
                            numPerElement[position]++;
                        }
                    }
                    else if(restriction.equals("face")){
                        if (elements.contains(windowFrame.getDice(i,j).getFace())) {
                            int position = elements.indexOf(windowFrame.getDice(i,j).getFace());
                            numPerElement[position]++;
                        }
                    }
                }
            }
        }

        int min = numPerElement[0];
        for (Integer c: numPerElement) {
            if(c<=min) {
                min = c;
            }
        }
        return  min*points;
    }

    @Override
    public String toString() {
        String string = super.toString();
        String emptyDiceSymbol =  "\uD83E\uDD76";
        string = string.concat("\nElements: ");
        for (String c: elements) {
            if (restriction.equals("face")) {
                string = string.concat( c + Color.RESET + " ");
            } else {
                string = string.concat(c + emptyDiceSymbol + Color.RESET + " ");
            }
        }
        return string;
    }
}
