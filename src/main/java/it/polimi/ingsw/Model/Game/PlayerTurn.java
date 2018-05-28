package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerTurn implements Serializable {
    private Table table;
    private Player player;
    private ArrayList<Move> moves;
    private ArrayList<PlayerTurn> playerTurns;

    public PlayerTurn(Player player, Table table) {
        this.table = table;
        this.player = player;
        this.playerTurns = new ArrayList<>();
        moves = new ArrayList<>();
        moves.add(new Move(table, player));
        //moves.add(new SpecialMove(draftPool, player, roundTrack, diceBag, playerTurns));
    }


    public ArrayList<PlayerTurn> getPlayerTurns() {
        return playerTurns;
    }

    public void setPlayerTurns(ArrayList<PlayerTurn> playerTurns) {
        this.playerTurns = playerTurns;
        for(Move move: moves) {
            move.setPlayerTurns(playerTurns);
        }
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public Move getMove(int index) {
        return moves.get(index);
    }

    public void addMove(Move move) {
        this.moves.add(move);
    }

    public boolean removeMove(Move move) {
        return moves.remove(move);
    }

    public Player getPlayer() {
        return player;
    }

    public void payTokens(int toPay) throws  NotValidInputException{
        player.setNumOfTokens(player.getNumOfTokens()-toPay);
    }

    @Override
    public String toString(){
        String string="DraftPool:\n";
        if(table.getDraftPool()==null)
            string=string.concat("NOT ADDED YET");
        else
            for(Dice dice: table.getDraftPool())
                string=string.concat(dice.toString());
        string=string.concat("\nPlayer: "+player.getName());
        return string;
    }

    public int size() {
        return moves.size();
    }


    public void dump(){
        System.out.println(toString());
    }


}