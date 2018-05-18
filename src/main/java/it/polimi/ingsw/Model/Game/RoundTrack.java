package it.polimi.ingsw.Model.Game;

import java.util.ArrayList;

public class RoundTrack {
    private ArrayList<ArrayList<Dice>> roundTrack;

    public RoundTrack() {
        roundTrack = new ArrayList<>();
        for(int i = 0; i<10; i++) {
            roundTrack.add(new ArrayList<>());
        }
    }

    public ArrayList<Dice> getRoundDices(int indexRT) {
        //inedxRT start from value 0
        return roundTrack.get(indexRT);
    }

    public void setRoundDices(ArrayList<Dice> roundDices, int indexRT) {
        //inedxRT start from value 0
        roundTrack.add(indexRT, roundDices);
    }

    public boolean isEmpty() {
        return roundTrack.isEmpty();
    }

    public int size() {
        return roundTrack.size();
    }

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

}