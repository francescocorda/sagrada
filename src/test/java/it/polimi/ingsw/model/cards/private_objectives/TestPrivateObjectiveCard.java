package it.polimi.ingsw.model.cards.private_objectives;

import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.model.game.Dice;
import it.polimi.ingsw.model.game.WindowFrame;
import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.patterns.PatternDeck;
import it.polimi.ingsw.database.ParserManager;
import it.polimi.ingsw.exceptions.InvalidFirstMoveException;
import it.polimi.ingsw.exceptions.InvalidNeighboursException;
import it.polimi.ingsw.exceptions.MismatchedRestrictionException;
import it.polimi.ingsw.exceptions.OccupiedCellException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestPrivateObjectiveCard  {

    private ParserManager pm = ParserManager.getParserManager();

    @Test
    void countScoreTest() {
        WindowFrame window = new WindowFrame();
        PatternDeck deck = new PatternDeck(pm.getPatternDeck());
        //deck.createPatternDeck();
        PatternCard pattern = deck.getPatternDeck().get(10);
        pattern.dump();
        System.out.println("--------");
        window.setPatternCard(pattern);
        window.dump();
        Dice dice1 = new Dice(Color.PURPLE);
        dice1.roll();
        Dice dice2 = new Dice(Color.PURPLE);
        dice2.roll();
        Dice dice3 = new Dice(Color.PURPLE);
        dice3.roll();
        Dice dice4 = new Dice(Color.PURPLE);
        dice4.roll();

        PrivateObjectiveCard purple = new PrivateObjectiveCard("purple",4, Color.PURPLE);

        try {
            window.setDice(1,2, dice1);
            window.setDice(2,3, dice2);
            window.setDice(3,4, dice3);
            window.setDice(2,5, dice4);
        } catch (MismatchedRestrictionException
                | OccupiedCellException
                | InvalidFirstMoveException
                | InvalidNeighboursException e) {
            e.printStackTrace();
        }

        window.dump();
        int score = dice1.valueOf()+dice2.valueOf()+dice3.valueOf()+dice4.valueOf();
        assertEquals(score, purple.countScore(window));

    }
}
