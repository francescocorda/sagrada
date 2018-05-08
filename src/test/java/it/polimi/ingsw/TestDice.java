package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.InvalidFaceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class TestDice {
    @Test
    void colorTest() {
        Dice dice = new Dice(Color.ANSI_RED);
        dice.roll();
        assertEquals(Color.ANSI_RED, dice.getColor());
        dice.setColor(Color.ANSI_BLUE);
        assertEquals(Color.ANSI_BLUE, dice.getColor());
        dice.setColor(Color.ANSI_PURPLE);
        assertEquals(Color.ANSI_PURPLE, dice.getColor());
        dice.setColor(Color.ANSI_GREEN);
        assertEquals(Color.ANSI_GREEN, dice.getColor());
        dice.setColor(Color.ANSI_YELLOW);
        assertEquals(Color.ANSI_YELLOW, dice.getColor());
    }

    @Test
    void numbersTest() {
        Dice dice = new Dice(Color.ANSI_RED);
        dice.roll();
        assertTrue((dice.valueOf() > 0) && (dice.valueOf() < 7));
    }

    @Test
    void oppositeFaceTest() {
        Dice dice = new Dice(Color.ANSI_YELLOW);
        dice.roll();
        for (int i = 1; i < 7; i++) {
            try {
                dice.setFace(i);
            } catch (InvalidFaceException e){
                e.printStackTrace();
            }
            assertEquals(6 - dice.valueOf() + 1, dice.getOppositeFace().compareTo("\u2680") + 1);
        }
    }

    @Test
    void setFaceTest() {
        Dice dice = new Dice(Color.ANSI_YELLOW);
        assertThrows(InvalidFaceException.class, ()->{dice.setFace(7);});
        assertThrows(InvalidFaceException.class, ()->{dice.setFace(-1);});
    }


}