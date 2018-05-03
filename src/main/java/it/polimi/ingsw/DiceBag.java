package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Random;

public class DiceBag {
    private ArrayList<Dice> dices;

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
        int count = this.dices.size();
        if (count == 0)
            throw new IndexOutOfBoundsException();
        Random rand = new Random();
        int index = rand.nextInt(count);
        Dice dice = this.dices.get(index);
        this.dices.remove(dice);
        return dice;
    }

    ArrayList<Dice> draw(int numberOfDice) {
        ArrayList<Dice> drawPool = new ArrayList<>();

        if(numberOfDice>this.dices.size())
            throw new IndexOutOfBoundsException();

        for(int i=0; i<numberOfDice; i++) {
            int count = this.dices.size();
            Random rand = new Random();
            int index = rand.nextInt(count);
            Dice dice = this.dices.get(index);
            this.dices.remove(dice);
            drawPool.add(dice);
        }
        return drawPool;
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

