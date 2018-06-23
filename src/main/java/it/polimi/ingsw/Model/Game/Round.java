package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Round implements Serializable {
    private static final int INITIAL_TURN_NUMBER = 1;
    private ArrayList<Player> players;
    private ArrayList<PlayerTurn> playerTurns;


    public Round(ArrayList<Player> players, int first){
        this.players =  players;
        playerTurns = new ArrayList<>();

        int indexPlayers = first;
        int turnNumber = INITIAL_TURN_NUMBER;

        for (int indexTurn = 0; indexTurn<players.size()*2; indexTurn++) {
            PlayerTurn turn = new PlayerTurn(players.get(indexPlayers), turnNumber);
            playerTurns.add(turn);
            if (indexTurn < players.size()-1) {
                indexPlayers = (indexPlayers+1)%players.size();
            } else if(indexTurn> players.size()-1) {
                turnNumber++;
                indexPlayers = (abs(indexPlayers-1))%players.size();
            }
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
        this.playerTurns.remove(0);
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
        String string = "RoundTrack:\n"/*+roundTrack.toString()*/;
        //string=string.concat("\nDiceBag:\n"+diceBag.toString());
        int index=0;
        string=string.concat("\nPLAYERS:\n");
        /*for(Player player : players){
            index++;
            string=string.concat("\nPlayer N"+index+":\n");
            string=string.concat(player.toString()+"\n");
        }*/
        //string=string.concat("\nDrawPool: ");
        //if(draftPool==null)
        string=string.concat("NOT ADDED YET");
        //else
        //for(Dice dice : draftPool)
        //string=string.concat(dice.toString()+" ");
        string=string.concat("\n\nPLAYER TURNS:\n");
        index=0;
        for(PlayerTurn playerTurn : playerTurns){
            index++;
            string=string.concat("\nPlayerTurn N"+index+":\n");
            string=string.concat(playerTurn.toString()+"\n");
        }
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }
}