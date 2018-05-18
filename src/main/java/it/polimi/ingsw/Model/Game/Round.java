package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.exceptions.NotValidInputException;

import java.util.ArrayList;

public class Round {
    private RoundTrack roundTrack;
    private DiceBag diceBag;
    private ArrayList<Dice> draftPool;
    private ArrayList<Player> players;
    private ArrayList<PlayerTurn> playerTurns;


    public Round(ArrayList<Player> players, int first, DiceBag diceBag, RoundTrack roundTrack) {
        this.roundTrack=roundTrack;
        this.diceBag=diceBag;
        this.draftPool = null;
        this.players =  players;
        playerTurns = new ArrayList<>();

        int indexPlayers = first;
        int indexTurn = 0;
        while (indexTurn<players.size()) {
            PlayerTurn turn = new PlayerTurn(players.get(indexPlayers), draftPool, this.roundTrack, this.diceBag);
            turn.setPlayerTurns(playerTurns);
            playerTurns.add(indexTurn, turn);

            if(indexPlayers==players.size()-1) {
                indexPlayers=0;
            } else {
                indexPlayers++;
            }

            indexTurn++;
        }

        while (indexTurn<players.size()*2) {
            PlayerTurn turn = new PlayerTurn(players.get(indexPlayers), draftPool, this.roundTrack, this.diceBag);
            turn.setPlayerTurns(playerTurns);
            playerTurns.add(indexTurn, turn);

            if(indexPlayers==0) {
                indexPlayers=players.size()-1;
            } else {
                indexPlayers--;
            }

            indexTurn++;
        }
    }

    public void removeTurn(PlayerTurn playerTurn) throws NotValidInputException {
        if(playerTurns.contains(playerTurn)) {
            this.playerTurns.remove(playerTurn);
        } else {
            throw new NotValidInputException();
        }
    }

    public void setDrawPool(){
        draftPool=diceBag.draw(2*players.size()+1);
    }

    public ArrayList<Dice> getDrawPool(){
        return this.draftPool;
    }

    public void insertDice(Dice dice){
        if(dice==null) throw new NullPointerException();
        draftPool.add(dice);
    }

    public Dice removeDice(int index) throws IndexOutOfBoundsException{
        if (index<0 || index> draftPool.size()-1) throw new IndexOutOfBoundsException();
        return draftPool.remove(index);
    }

    public Dice getDice(int index)throws IndexOutOfBoundsException{
        if (index<0 || index> draftPool.size()-1) throw new IndexOutOfBoundsException();
        return draftPool.get(index);
    }

    public void updateRoundTrack(int indexRT){
        ArrayList<Dice> roundDices = roundTrack.getRoundDices(indexRT);
        roundDices = draftPool;
    }

    public RoundTrack getRoundTrack(){
        return this.roundTrack;
    }

    public DiceBag getDiceBag(){
        return this.diceBag;
    }

    public ArrayList<PlayerTurn> getPlayerTurns(){
        return this.playerTurns;
    }

    public String roundTrackToString(){
        if(roundTrack.isEmpty())
            return "ROUND  1:\nROUND  2:\nROUND  3:\nROUND  4:\nROUND  5:\nROUND  6:\nROUND  7:\nROUND  8:\nROUND  9:\nROUND 10:\n";
        String string="";
        for(int i=0; i<roundTrack.size(); i++){
            string=string.concat("ROUND "+(i<10 ? " " : "")+i+1+": ");
            for(int j=0; roundTrack.getRoundDices(i).get(j) != null; j++)
                string=string.concat(roundTrack.getRoundDices(i).get(j).toString()+ " ");
            string=string.concat("\n");
        }
        return string;
    }

    @Override
    public String toString(){
        String string = "RoundTrack:\n"+roundTrackToString();
        string=string.concat("\nDiceBag:\n"+diceBag.toString());
        int index=0;
        string=string.concat("\nPLAYERS:\n");
        for(Player player : players){
            index++;
            string=string.concat("\nPlayer N"+index+":\n");
            string=string.concat(player.toString()+"\n");
        }
        string=string.concat("\nDrawPool: ");
        if(draftPool==null)
            string=string.concat("NOT ADDED YET");
        else
            for(Dice dice : draftPool)
                string=string.concat(dice.toString()+" ");
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