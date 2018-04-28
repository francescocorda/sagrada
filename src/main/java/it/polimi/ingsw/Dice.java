package it.polimi.ingsw;

import java.util.Random;

public class Dice {
    private Color color;
    private String face;

    public Dice(Color color) {
        this.color = color;
        //roll();
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

    public void roll(){
        int count = faces.length;
        Random rand = new Random();
        int index = rand.nextInt(count);
        this.face = faces[index];
    }
}
