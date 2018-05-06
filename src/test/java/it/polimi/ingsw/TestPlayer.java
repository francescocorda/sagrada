package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void setNumberOfTokensTest() throws NotValidInputException {
        Player player1 = new Player("player");
        try {
            player1.setNumOfTokens(1);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }

        assertEquals(1, player1.getNumOfTokens());

        assertThrows(NotValidInputException.class, ()->player1.setNumOfTokens(-1));


        /*player1.dump();
        PrivateObjectiveDeck deck = new PrivateObjectiveDeck();
        deck.dump();
        Random rand = new Random();
        int index = rand.nextInt(5);
        player1.setPrivateObjectiveCard(deck.getPrivateObjectiveCard(index));
        PatternDeck patternDeck = new PatternDeck();
        patternDeck.dump();
        index=rand.nextInt(24);
        WindowFrame frame = new WindowFrame();
        frame.setPatternCard(patternDeck.getPatternCard(index));
        player1.setWindowFrame(frame);
        player1.dump();*/

    }
}
