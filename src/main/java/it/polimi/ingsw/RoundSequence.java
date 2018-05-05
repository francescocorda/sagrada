package it.polimi.ingsw;

import java.util.ArrayList;

public class RoundSequence {
    private Round[] rounds;
    private ArrayList<Player> players;
    private ArrayList<ArrayList<Dice>> roundTrack;

    DiceBag diceBag;

    public RoundSequence(ArrayList<Player> players, DiceBag diceBag) {
        roundTrack=new ArrayList<>(10);
        this.diceBag=diceBag;
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
}