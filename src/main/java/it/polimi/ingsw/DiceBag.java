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

    void dump() {
        int count = dices.size();
        System.out.print("elems: ");
        System.out.println(count);
        for (Dice d : dices) {
            System.out.println(d);
            // or:
            // d.dump();
        }
    }

    public int size(){
        return dices.size();
    }

}

