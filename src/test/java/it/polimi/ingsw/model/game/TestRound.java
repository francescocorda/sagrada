package it.polimi.ingsw.model.game;

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
        Round round = new Round(players, 0);

        PlayerTurn turn = round.getPlayerTurns().get(3);
        int size = round.getPlayerTurns().size();
        round.removeTurn(round.getPlayerTurns().get(3));


        assertEquals(size-1, round.getPlayerTurns().size());
        assertTrue(!round.getPlayerTurns().contains(turn));

    }
}
