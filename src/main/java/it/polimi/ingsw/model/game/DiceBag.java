package it.polimi.ingsw.model.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static it.polimi.ingsw.model.game.Color.NUMBER_OF_COLORS;

public class DiceBag implements Serializable {
    public static final int MAX_NUMBER_OF_DICES = 90;
    private ArrayList<Dice> dices;

    /**
     * creates a new {@link DiceBag}.
     */
    public DiceBag() {
        dices = new ArrayList<>();
        fill();
    }

    /**
     * creates a new {@link DiceBag} from the given {@link DiceBag} diceBag.
     * @param diceBag : the given {@link DiceBag} diceBag
     */
    public DiceBag(DiceBag diceBag) {
        this.dices = new ArrayList<>();
        for (Dice dice: diceBag.getDices()) {
            dices.add(new Dice(dice));
        }
    }

    /**
     * @return {@link #dices}.
     */
    private ArrayList<Dice> getDices() {
        return dices;
    }

    /**
     * fills this {@link DiceBag}.
     */
    private void fill() {
        for (int i = 0; i < MAX_NUMBER_OF_DICES / NUMBER_OF_COLORS; i++)
            for (Color c : Color.values()) {
                Dice d = new Dice(c);
                this.dices.add(d);
            }
    }

    /**
     * @return a random {@link Dice} extracted from {@link #dices}.
     */
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

    /**
     * @param numberOfDice : the given int numberOfDice
     * @return an {@link ArrayList<Dice>} of the given int numberOfDice dices extracted from {@link #dices}.
     */
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

    /**
     * adds the given {@link Dice} dice to {@link #dices}.
     * @param dice : the given {@link Dice} dice
     */
    public void addDice(Dice dice) {
        this.dices.add(dice);
    }

    /**
     * @return the {@link String} representation of this {@link DiceBag}.
     */
    @Override
    public String toString(){
        String string = "";
        for(Dice temp: dices){
            string = string.concat(temp.toString()+"\n");
        }
        return "elems: "+dices.size()+"\n"+string;
    }

    /**
     * displays {@link #toString()}.
     */
    void dump() {
        System.out.println(this);
    }

    /**
     * @return {@link #dices} size.
     */
    public int size(){
        return dices.size();
    }
}

