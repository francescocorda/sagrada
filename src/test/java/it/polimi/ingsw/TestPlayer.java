package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void firstTest() {
        Player player1 = new Player("player");
        try {
            player1.setNumOfTokens(1);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }

        assertEquals(1, player1.getNumOfTokens());

        assertThrows(NotValidInputException.class, ()->player1.setNumOfTokens(-1));
    }
}
