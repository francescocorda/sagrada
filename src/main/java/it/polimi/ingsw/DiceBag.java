package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Random;

public class DiceBag {
    ArrayList<Dice> dices;

    public DiceBag() {
        dices = new ArrayList<Dice>();
        fill();
    }

    private void fill() {
        for (int i = 0; i < 90 / 5; i++)
            for (Color c : Color.values()) {
                Dice d = new Dice(c);
                this.dices.add(d);
            }
    }

    Dice draw() {
        int count = dices.size();
        if (count == 0)
            return null;
        Random rand = new Random();
        int index = rand.nextInt(count);
        Dice d = dices.get(index);
        this.dices.remove(d);
        return d;
    }

    @Override
    public String toString(){
        String string = "";
        for(Dice temp: dices){
            string = string.concat(temp.toString()+"\n");
        }
        return "elems: "+dices.size()+"\n"+string;
    }

    void dump() {
        System.out.println(this);
    }

    public int size(){
        return dices.size();
    }
}

