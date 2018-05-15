package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayerTurn {

    @Test
    void myTest() {
        Player player = new Player("fra");

        try {
            player.setNumOfTokens(6);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }

        ArrayList<Dice> drawpool = new ArrayList<>();
        for(int i=0; i<9;i++) {
            drawpool.add(new Dice(Color.ANSI_BLUE));
            drawpool.get(i).roll();
        }
        PlayerTurn turn = new PlayerTurn(player, drawpool);

        assertEquals(player, turn.getPlayer());

        turn.setActionPerformed(ActionPerformed.NOTHING);
        assertEquals(ActionPerformed.NOTHING, turn.getActionPerformed());

        try {
            turn.payTokens(6);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }

        assertEquals(0, turn.getPlayer().getNumOfTokens());

        //turn.dump();

        assertThrows(NotValidInputException.class,()->turn.payTokens(1));
    }
}
