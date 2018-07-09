package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.InvalidFaceException;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {
    private Color color;
    private String face;
    private int value;

    /**
     * creates a new {@link Dice} from the given {@link Color} color.
     * @param color : the given {@link Color} color
     */
    public Dice(Color color) {
        this.color = color;
        this.face=null;
        this.value= -1;
    }

    /**
     * creates a new {@link Dice} from the given {@link Dice} dice.
     * @param dice : the given {@link Dice} dice
     */
    public Dice(Dice dice) {
        this.color = dice.getColor();
        this.face = dice.getFace();
        this.value = dice.value;
    }

    /**
     * @return {@link #color}
     */
    public Color getColor() {
        return color;
    }

    /**
     * sets {@link #color} as the value of the given {@link Color} color.
     * @param color : the given {@link Color} color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the representation of this {@link Dice}.
     */
    @Override
    public String toString() {
        String escape = this.color.escape();
        return escape+"["+face+"]" + Color.RESET;
    }

    /**
     * @return {@link #face}.
     */
    public String getFace(){
        return face;
    }

    /**
     * sets {@link #face} to the given int value.
     * @param value the given int value
     * @throws InvalidFaceException if the given value is not valid
     */
    public void setFace(int value) throws InvalidFaceException{
        if(value>0 && value<7){
            this.face=faces[value-1];
            this.value=value;
        }
        else
            throw new InvalidFaceException();
    }

    /**
     * displays {@link #toString()}.
     */
    void dump(){
        System.out.println(toString());
    }

    public static final String[] faces = {
            "\u2680",  //ONE
            "\u2681",  //TWO
            "\u2682",  //THREE
            "\u2683",  //FOUR
            "\u2684",  //FIVE
            "\u2685"   //SIX
    };

    /**
     * @return the int value of {@link #face}.
     */
    public int valueOf(){
        return value;
    }

    /**
     * rolls this {@link Dice}.
     */
    public void roll(){
        int count = faces.length;
        Random rand = new Random();
        int index = rand.nextInt(count);
        this.value = index+1;
        this.face = faces[index];
    }

}
