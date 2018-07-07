package it.polimi.ingsw.model.game;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Round implements Serializable {
    private ArrayList<PlayerTurn> playerTurns;


    public Round(ArrayList<Player> players, int first){
        playerTurns = new ArrayList<>();
        int indexPlayers = first;

        for (int indexTurn = 0; indexTurn<players.size(); indexTurn++) {
            PlayerTurn turn = new PlayerTurn(players.get(indexPlayers), 1);
            playerTurns.add(turn);
            if(indexTurn != players.size()-1) {
                if (indexPlayers<players.size()-1)
                    indexPlayers++;
                else
                    indexPlayers = 0;
            }
        }
        for (int indexTurn = players.size(); indexTurn<players.size()*2; indexTurn++) {
            PlayerTurn turn = new PlayerTurn(players.get(indexPlayers), 2);
            playerTurns.add(turn);
            if(indexPlayers>0)
                indexPlayers--;
            else
                indexPlayers = players.size()-1;
        }
    }

    public Player getCurrentPlayer() {
        return playerTurns.get(0).getPlayer();
    }

    public int size() {
        return playerTurns.size();
    }

    public void removeTurn(PlayerTurn playerTurn) {
        this.playerTurns.remove(playerTurn);
    }

    public void removeTurn(int index) {
        this.playerTurns.remove(index);
    }


    public ArrayList<PlayerTurn> getPlayerTurns(){
        return this.playerTurns;
    }

    public void setPlayerTurns(ArrayList<PlayerTurn> playerTurns) {
        this.playerTurns = playerTurns;
    }

    public PlayerTurn getPlayerTurn(int index) {
        return playerTurns.get(index);
    }



    @Override
    public String toString(){
        String string= "\n\nPLAYER TURNS:\n";
        int index=0;
        for(PlayerTurn playerTurn : playerTurns){
            index++;
            string=string.concat("\nPlayerTurn N "+index+":\n");
            string=string.concat(playerTurn.toString()+"\n");
        }
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }
}