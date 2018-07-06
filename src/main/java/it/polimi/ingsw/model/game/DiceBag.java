package it.polimi.ingsw.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.model.game.Color.NUMBER_OF_COLORS;

public class DiceBag implements Serializable {
    public static final int MAX_NUMBER_OF_DICES = 90;
    private ArrayList<Dice> dices;

    public DiceBag() {
        dices = new ArrayList<>();
        fill();
    }

    public DiceBag(DiceBag diceBag) {
        this.dices = new ArrayList<>();
        for (Dice dice: diceBag.getDices()) {
            dices.add(new Dice(dice));
        }
    }

    private ArrayList<Dice> getDices() {
        return dices;
    }
    private void fill() {
        for (int i = 0; i < MAX_NUMBER_OF_DICES / NUMBER_OF_COLORS; i++)
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

