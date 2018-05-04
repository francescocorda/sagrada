package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPatternDeck {

    @Test
    public void removePatternCard(){
        PatternDeck patternDeck = new PatternDeck();
        patternDeck.createPatternDeck();
        assertEquals(24, patternDeck.getPatternDeck().size());
        assertThrows(IndexOutOfBoundsException.class, ()->patternDeck.removePatternCard(25));

        assertDoesNotThrow(()->patternDeck.removePatternCard(15));
        assertEquals(23, patternDeck.getPatternDeck().size());
        assertThrows(IndexOutOfBoundsException.class, ()->patternDeck.removePatternCard(-1));
        assertThrows(IndexOutOfBoundsException.class, ()->patternDeck.removePatternCard(25));
    }

}
