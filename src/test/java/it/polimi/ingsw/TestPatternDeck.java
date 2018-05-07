package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPatternDeck {

    @Test
    void removePatternCard(){
        PatternDeck patternDeck = new PatternDeck();
        //patternDeck.createPatternDeck();
        assertEquals(24, patternDeck.getPatternDeck().size());
        assertThrows(IndexOutOfBoundsException.class, ()->patternDeck.removePatternCard(25));

        assertDoesNotThrow(()->patternDeck.removePatternCard(15));
        assertEquals(23, patternDeck.getPatternDeck().size());
        assertThrows(IndexOutOfBoundsException.class, ()->patternDeck.removePatternCard(-1));
        assertThrows(IndexOutOfBoundsException.class, ()->patternDeck.removePatternCard(25));
    }


    @Test
    void myTest() {
        PatternDeck patternDeck = new PatternDeck();
        patternDeck.dump();
        try {
            PatternCard patternCard = patternDeck.getPatternCard(4);
            patternCard.dump();
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }


    }
}
