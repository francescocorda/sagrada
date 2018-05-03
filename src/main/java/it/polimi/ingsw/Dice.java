package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.InvalidFaceException;

import java.util.Random;

public class Dice {
    private Color color;
    private String face;

    public Dice(Color color) {
        this.color = color;
        roll();
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


    public String getOppositeFace(){
        return faces[6-valueOf()];
    }

    public void setOppositeFace(){
        this.face = faces[6-valueOf()];
    }

    public void setFace(int value) throws InvalidFaceException{
        if(0<value && value<7){
            this.face=faces[value-1];
        }
        else
            throw new InvalidFaceException();
    }

    void dump(){
        System.out.println(this);
    }

    public static final String[] faces = {
            "\u2680",
            "\u2681",
            "\u2682",
            "\u2683",
            "\u2684",
            "\u2685"
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
