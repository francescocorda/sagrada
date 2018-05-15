package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;

import java.util.ArrayList;

public class Round {
    private ArrayList<ArrayList<Dice>> roundTrack;
    private DiceBag diceBag;
    private ArrayList<Dice> drawPool;
    private ArrayList<Player> players;
    private ArrayList<PlayerTurn> playerTurns;


    public Round(ArrayList<Player> players, int first, DiceBag diceBag, ArrayList<ArrayList<Dice>> roundTrack) {
        this.roundTrack=roundTrack;
        this.diceBag=diceBag;
        this.drawPool = null;
        this.players =  players;
        playerTurns = new ArrayList<>();

        int indexPlayers = first;
        int indexTurns = 0;
        while (indexTurns<players.size()) {
            playerTurns.add(indexTurns, new PlayerTurn(players.get(indexPlayers), drawPool));

            if(indexPlayers==players.size()-1) {
                indexPlayers=0;
            } else {
                indexPlayers++;
            }

            indexTurns++;
        }

        while (indexTurns<players.size()*2) {
            playerTurns.add(indexTurns, new PlayerTurn(players.get(indexPlayers), drawPool));

            if(indexPlayers==0) {
                indexPlayers=players.size()-1;
            } else {
                indexPlayers--;
            }

            indexTurns++;
        }
    }

    public void removeTurn(PlayerTurn playerTurn) throws NotValidInputException {
        if(playerTurns.contains(playerTurn)) {
            this.playerTurns.remove(playerTurn);
        } else {
            throw new NotValidInputException();
        }
    }

    public void twoTurnsInARow(Player player) throws NotValidInputException {

        if(!players.contains(player)) {
            throw new NotValidInputException();
        }

        int i = 0;
        while(!playerTurns.get(i).getPlayer().equals(player) && i<playerTurns.size()) {
            i++;
        }
        int j = i+1;
        while (!playerTurns.get(j).getPlayer().equals(player) && j<playerTurns.size()) {
            j++;
        }

        if(j<playerTurns.size()) {
            playerTurns.add(i+1, playerTurns.remove(j));
        } else {
            throw new IndexOutOfBoundsException();
        }

    }

    public void setDrawPool(){
        drawPool=diceBag.draw(2*players.size()+1);
    }

    public ArrayList<Dice> getDrawPool(){
        return this.drawPool;
    }

    public void insertDice(Dice dice){
        if(dice==null) throw new NullPointerException();
        drawPool.add(dice);
    }

    public Dice removeDice(int index) throws IndexOutOfBoundsException{
        if (index<0 || index> drawPool.size()-1) throw new IndexOutOfBoundsException();
        return drawPool.remove(index);
    }

    public Dice getDice(int index)throws IndexOutOfBoundsException{
        if (index<0 || index> drawPool.size()-1) throw new IndexOutOfBoundsException();
        return drawPool.get(index);
    }

    public void updateRoundTrack(){
        roundTrack.add(drawPool);
    }

    public ArrayList<ArrayList<Dice>> getRoundTrack(){
        return this.roundTrack;
    }

    public DiceBag getDiceBag(){
        return this.diceBag;
    }

    public ArrayList<PlayerTurn> getPlayerTurn(){
        return this.playerTurns;
    }

    public String roundTrackToString(){
        if(roundTrack.isEmpty())
            return "ROUND  1:\nROUND  2:\nROUND  3:\nROUND  4:\nROUND  5:\nROUND  6:\nROUND  7:\nROUND  8:\nROUND  9:\nROUND 10:\n";
        String string="";
        for(int i=0; i<roundTrack.size(); i++){
            string=string.concat("ROUND "+(i<10 ? " " : "")+i+1+": ");
            for(int j=0; roundTrack.get(i).get(j)!=null; j++)
                string=string.concat(roundTrack.get(i).get(j).toString()+" ");
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
        if(drawPool==null)
            string=string.concat("NOT ADDED YET");
        else
            for(Dice dice : drawPool)
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