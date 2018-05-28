package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Round;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestRound {
    @Test
    public void removeTurnTest() {
        Table table = new Table();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1"));
        players.add(new Player("player2"));
        players.add(new Player("player3"));
        players.add(new Player("player4"));
        Round round = new Round(players, 0, table);

        PlayerTurn turn = round.getPlayerTurns().get(3);
        int size = round.getPlayerTurns().size();
        try {
            round.removeTurn(round.getPlayerTurns().get(3));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }

        assertEquals(size-1, round.getPlayerTurns().size());
        assertTrue(!round.getPlayerTurns().contains(turn));

    }
}
