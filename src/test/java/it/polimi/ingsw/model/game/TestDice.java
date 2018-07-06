package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.InvalidFaceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class TestDice {
    @Test
    void colorTest() {
        Dice dice = new Dice(Color.RED);
        dice.roll();
        assertEquals(Color.RED, dice.getColor());
        dice.setColor(Color.BLUE);
        assertEquals(Color.BLUE, dice.getColor());
        dice.setColor(Color.PURPLE);
        assertEquals(Color.PURPLE, dice.getColor());
        dice.setColor(Color.GREEN);
        assertEquals(Color.GREEN, dice.getColor());
        dice.setColor(Color.YELLOW);
        assertEquals(Color.YELLOW, dice.getColor());
    }

    @Test
    void numbersTest() {
        Dice dice = new Dice(Color.RED);
        dice.roll();
        assertTrue((dice.valueOf() > 0) && (dice.valueOf() < 7));
    }


    @Test
    void setFaceTest() {
        Dice dice = new Dice(Color.YELLOW);
        assertThrows(InvalidFaceException.class, ()->{dice.setFace(7);});
        assertThrows(InvalidFaceException.class, ()->{dice.setFace(-1);});
    }


}
