package it.polimi.ingsw.Model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class DiceBag implements Serializable {
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

    public Dice draw() {
        int count = this.dices.size();
        if (count == 0)
            throw new IndexOutOfBoundsException();
        Random rand = new Random();
        int index = rand.nextInt(count);
        Dice dice = this.dices.get(index);
        this.dices.remove(dice);
        dice.roll();
        return dice;
    }

    public ArrayList<Dice> draw(int numberOfDice) {
        ArrayList<Dice> drawPool = new ArrayList<>();

        if(numberOfDice>this.dices.size())
            throw new IndexOutOfBoundsException();

        for(int i=0; i<numberOfDice; i++) {
            int count = this.dices.size();
            Random rand = new Random();
            int index = rand.nextInt(count);
            Dice dice = this.dices.get(index);
            this.dices.remove(dice);
            dice.roll();
            drawPool.add(dice);
        }
        return drawPool;
    }

    public void addDice(Dice dice) {
        this.dices.add(dice);
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

