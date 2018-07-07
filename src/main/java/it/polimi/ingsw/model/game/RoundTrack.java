package it.polimi.ingsw.model.game;

import java.io.Serializable;
import java.util.ArrayList;

public class RoundTrack implements Serializable {
    private ArrayList<ArrayList<Dice>> roundTrack;
    public static final int NUMBER_OF_ROUNDS = 10;

    /**
     * creates a new {@link RoundTrack}.
     */
    public RoundTrack() {
        roundTrack = new ArrayList<>();
        for(int i = 0; i< NUMBER_OF_ROUNDS; i++) {
            roundTrack.add(new ArrayList<>());
        }
    }

    /**
     * creates a new {@link RoundTrack} from the given {@link RoundTrack} roundTrack.
     * @param roundTrack : the given {@link RoundTrack} roundTrack
     */
    public RoundTrack(RoundTrack roundTrack) {
        this.roundTrack = new ArrayList<>();
        for (int i=0; i<roundTrack.size(); i++) {
            this.roundTrack.add(new ArrayList<>());
            for (int j=0; j<roundTrack.getRoundDices(i).size(); j++) {
                this.roundTrack.get(i).add(new Dice(roundTrack.getRoundDices(i).get(j)));
            }
        }
    }

    /**
     * @param indexRT : the given int indexRT
     * @return the {@link Dice} at the given int indexRT of {@link #roundTrack}.
     */
    public ArrayList<Dice> getRoundDices(int indexRT) {
        return roundTrack.get(indexRT);
    }

    /**
     * sets the given {@link ArrayList<Dice>} roundDices as the {@link ArrayList<Dice>} at the given
     * int indexRT of {@link #roundTrack}.
     * @param roundDices : the given {@link ArrayList<Dice>} roundDices
     * @param indexRT : the given int indexRT
     */
    public void setRoundDices(ArrayList<Dice> roundDices, int indexRT) {
        //indexRT start from value 0
        ArrayList<Dice> dices = new ArrayList<>();
        for (Dice dice: roundDices) {
            dices.add(new Dice(dice));
        }
        roundTrack.set(indexRT, dices);
    }

    /**
     * @return if {@link #roundTrack} is empty or not.
     */
    public boolean isEmpty() {
        return roundTrack.isEmpty();
    }

    /**
     * @return {@link #roundTrack}'s int size.
     */
    public int size() {
        return roundTrack.size();
    }

    /**
     * @return the {@link String} representation of this {@link RoundTrack}.
     */
    @Override
    public String toString(){
        if(roundTrack.isEmpty())
            return "DEBUG:ROUND  1:\nROUND  2:\nROUND  3:\nROUND  4:\nROUND  5:\nROUND  6:\nROUND  7:\nROUND  8:\nROUND  9:\nROUND 10:\n";
        String string="";
        for(int i=0; i<roundTrack.size(); i++){
            string=string.concat("ROUND "+(i< NUMBER_OF_ROUNDS -1 ? " " : "")+ (i+1) +": ");
            for(int j=0; j<roundTrack.get(i).size(); j++) {
                string = string.concat(roundTrack.get(i).get(j).toString() + " ");
            }
            string=string.concat("\n");
        }
        return string;
    }

    /**
     * displays {@link #toString()}.
     */
    public void dump(){
        System.out.println(toString());
    }
}