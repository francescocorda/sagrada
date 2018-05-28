package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.exceptions.InvalidFaceException;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {
    private Color color;
    private String face;

    public Dice(Color color) {
        this.color = color;
        this.face=null;
    }

    public Dice(Dice dice) {
        this.color = dice.getColor();
        this.face = dice.getFace();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        String escape = this.color.escape();
        return escape+"["+face+"]" + Color.RESET;
    }

    public String getFace(){
        return face;
    }

    public void setFace(int value) throws InvalidFaceException{
        if(value>0 && value<7){
            this.face=faces[value-1];
        }
        else
            throw new InvalidFaceException();
    }

    void dump(){
        System.out.println(toString());
    }

    protected static final String[] faces = {          //it was public before (changed on sonarqube's suggestion)
            "\u2680",  //ONE
            "\u2681",  //TWO
            "\u2682",  //THREE
            "\u2683",  //FOUR
            "\u2684",  //FIVE
            "\u2685"   //SIX
    };

    public int valueOf(){
        return face.compareTo("\u2680")+1;
    }

    public void roll(){
        int count = faces.length;
        Random rand = new Random();
        int index = rand.nextInt(count);
        this.face = faces[index];
    }

}
