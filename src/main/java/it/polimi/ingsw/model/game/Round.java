package it.polimi.ingsw.model.game;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class Round implements Serializable {
    private ArrayList<PlayerTurn> playerTurns;

    /**
     * creates a new {@link Round} from the give parameters.
     * @param players : the given {@link ArrayList<Player>} players
     * @param first : the given int first
     */
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

    /**
     * @return {@link Player} of the {@link PlayerTurn} at index 0 of {@link #playerTurns}.
     */
    public Player getCurrentPlayer() {
        return playerTurns.get(0).getPlayer();
    }

    /**
     * @return int size of {@link #playerTurns}.
     */
    public int size() {
        return playerTurns.size();
    }

    /**
     * removes the given {@link PlayerTurn} playerTurn from {@link #playerTurns}.
     * @param playerTurn : the given {@link PlayerTurn} playerTurn
     */
    public void removeTurn(PlayerTurn playerTurn) {
        this.playerTurns.remove(playerTurn);
    }

    /**
     * removes the {@link PlayerTurn} at the given int index of {@link #playerTurns}.
     * @param index : the given int index
     */
    public void removeTurn(int index) {
        this.playerTurns.remove(index);
    }

    /**
     * @return {@link #playerTurns}
     */
    public ArrayList<PlayerTurn> getPlayerTurns(){
        return this.playerTurns;
    }

    /**
     * sets {@link #playerTurns} as the given {@link ArrayList<PlayerTurn>} playerTurns.
     * @param playerTurns : the given {@link ArrayList<PlayerTurn>} playerTurns
     */
    public void setPlayerTurns(ArrayList<PlayerTurn> playerTurns) {
        this.playerTurns = playerTurns;
    }

    /**
     * @param index : the given int index
     * @return the {@link PlayerTurn} at the given int indexof {@link #playerTurns}.
     */
    public PlayerTurn getPlayerTurn(int index) {
        return playerTurns.get(index);
    }

    /**
     * @return the {@link String} representation of this {@link Round}.
     */
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

    /**
     * displays {@link #toString()}
     */
    public void dump(){
        System.out.println(toString());
    }
}