package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPlayer {

    @Test
    void setNumberOfTokensTest() {
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

    @Test
    void creatorTest(){
        String name = "player";
        Player player = new Player(name);
        //Test that the name passed through the constructor is indeed he name expected
        assertEquals(name, player.getName());
        //Test that the default value of numOfTokens is 0
        assertEquals(0, player.getNumOfTokens());
        //Test that the default score is 0
        assertEquals(0, player.getScore());
        //Test that by default a player doesn't have any objective card
        assertEquals(null, player.getPrivateObjectiveCard());
        //Test that by defaul a player has an empty Dice matrix in the WindowFrame
        for(int i=0; i<5; i++)
            for(int j=0; j<4; j++)
                assertEquals(null, player.getWindowFrame().getDice(j+1, i+1));
        //Test that by default the patternCard of the windowsPlayer assigned to a player is null
        assertEquals(null, player.getWindowFrame().getPatternCard());
    }

    @Test
    void nameMethodsTest(){
        String name = "player";
        String test = "test";
        Player player = new Player(name);
        assertEquals(name, player.getName());
        player.setName(test);
        assertEquals(test, player.getName());
    }

    @Test
    void numOfTokensTest() {
        String name = "player";
        Player player = new Player(name);
        assertEquals(0, player.getNumOfTokens());
        try {
            player.setNumOfTokens(6);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(6, player.getNumOfTokens());
        try {
            player.setNumOfTokens(1);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(1, player.getNumOfTokens());
        assertThrows(NotValidInputException.class,()->{player.setNumOfTokens(-1);});
        assertDoesNotThrow(()->{player.setNumOfTokens(3);});
    }
}