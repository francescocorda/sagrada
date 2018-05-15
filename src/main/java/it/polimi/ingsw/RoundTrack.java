package it.polimi.ingsw;

import java.util.ArrayList;

public class RoundTrack {
    private ArrayList<ArrayList<Dice>> roundTrack;

    public RoundTrack() {
        roundTrack = new ArrayList<>();
    }

    public void addDice(Dice dice, int round_index) {}

    @Override
    public String toString(){
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

    public void dump(){
        System.out.println(toString());
    }


    /* OLD VERSION
    private Round[] rounds;
    private ArrayList<Player> players;
    private ArrayList<ArrayList<Dice>> roundTrack;
    private DiceBag diceBag;

    public RoundSequence(ArrayList<Player> players, DiceBag diceBag) {
        roundTrack=new ArrayList<>(10);
        this.diceBag=diceBag;
        this.players=players;
        rounds = new Round[10];
        int first = 0;
        for(int i=0; i<rounds.length; i++) {
            rounds[i] = new Round(players, first, diceBag, roundTrack);
            if(first == players.size()-1) {
                first = 0;
            } else {
                first++;
            }
        }
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
        String string = "ROUNDS:\n";
        for(int i=0; i<rounds.length; i++)
            string=string.concat("\n"+rounds[i].toString()+"\n");
        string=string.concat("\nPLAYERS:\n");
        for(Player player : players)
            string=string.concat("\n"+player.toString()+"\n");
        string=string.concat("\nRound Track:\n"+roundTrackToString());
        string=string.concat("\nDiceBag:\n"+diceBag.toString());
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }

    */
}