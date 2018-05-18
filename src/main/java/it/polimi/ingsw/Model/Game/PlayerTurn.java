package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerTurn {
    private ArrayList<Dice> draftPool;
    private Player player;
    private ArrayList<Move> moves;
    private ArrayList<PlayerTurn> playerTurns;
    private RoundTrack roundTrack;
    private DiceBag diceBag;

    public PlayerTurn(Player player, ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag) {
        this.draftPool = draftPool;
        this.player = player;
        this.playerTurns = null;
        this.roundTrack = roundTrack;
        this.diceBag = diceBag;
        moves = new ArrayList<>();
        moves.add(new Move(draftPool, player, playerTurns));
        moves.add(new SpecialMove(draftPool, player, roundTrack, diceBag, playerTurns));
    }

    public ArrayList<PlayerTurn> getPlayerTurns() {
        return playerTurns;
    }

    public void setPlayerTurns(ArrayList<PlayerTurn> playerTurns) {
        this.playerTurns = playerTurns;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
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
        if(draftPool==null)
            string=string.concat("NOT ADDED YET");
        else
            for(Dice dice: draftPool)
                string=string.concat(dice.toString());
        string=string.concat("\nPlayer: "+player.getName());
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }


}