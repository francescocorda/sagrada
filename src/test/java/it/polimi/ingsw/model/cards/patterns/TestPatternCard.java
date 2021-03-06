package it.polimi.ingsw.model.cards.patterns;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPatternCard {
    @Test
    void difficultyTest(){
        PatternCard patternCard=new PatternCard("Prova", 1);
        try {
            patternCard.setDifficulty(3);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(3, patternCard.getDifficulty());
        try {
            patternCard.setDifficulty(4);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(4, patternCard.getDifficulty());
        try {
            patternCard.setDifficulty(5);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(5, patternCard.getDifficulty());
        try {
            patternCard.setDifficulty(6);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        assertEquals(6, patternCard.getDifficulty());

        assertThrows(NotValidInputException.class, ()->patternCard.setDifficulty(-1));
        assertDoesNotThrow(()->patternCard.setDifficulty(0));
        assertDoesNotThrow(()->patternCard.setDifficulty(10));
    }
    @Test
    void restrictionTest(){
        PatternCard patternCard=new PatternCard("Prova", 1);
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(0, 0, Restriction.WHITE));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(-1, 3,Restriction.WHITE));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(3, -1,Restriction.WHITE));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(-1, 7,Restriction.WHITE));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(-1, -1,Restriction.WHITE));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(6, 6,Restriction.WHITE));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(4, 6,Restriction.WHITE));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setRestriction(5, 5,Restriction.WHITE));


        assertDoesNotThrow(()->patternCard.setRestriction(1,1,Restriction.WHITE));
        assertDoesNotThrow(()->patternCard.setRestriction(1,5,Restriction.WHITE));
        assertDoesNotThrow(()->patternCard.setRestriction(4,5,Restriction.WHITE));
        assertDoesNotThrow(()->patternCard.setRestriction(4,1,Restriction.WHITE));
        assertDoesNotThrow(()->patternCard.setRestriction(3,3,Restriction.WHITE));
    }

    @Test
    void ExceptionPositionTest(){
        PatternCard patternCard=new PatternCard("Prova", 1);
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(0, 0,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(-1, 3,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(3, -1,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(-1, 7,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(-1, -1,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(6, 6,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(4, 6,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionPosition(5, 5,true));


        assertDoesNotThrow(()->patternCard.setExceptionPosition(1,1,true));
        assertDoesNotThrow(()->patternCard.setExceptionPosition(1,5,true));
        assertDoesNotThrow(()->patternCard.setExceptionPosition(4,5,true));
        assertDoesNotThrow(()->patternCard.setExceptionPosition(4,1,true));
        assertDoesNotThrow(()->patternCard.setExceptionPosition(3,3,true));
    }

    @Test
    void ExceptionRestrictionTest(){
        PatternCard patternCard=new PatternCard("Prova", 1);
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(0, 0,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(-1, 3,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(3, -1,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(-1, 7,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(-1, -1,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(6, 6,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(4, 6,true));
        assertThrows(IndexOutOfBoundsException.class, ()->patternCard.setExceptionRestriction(5, 5,true));


        assertDoesNotThrow(()->patternCard.setExceptionRestriction(1,1,true));
        assertDoesNotThrow(()->patternCard.setExceptionRestriction(1,5,true));
        assertDoesNotThrow(()->patternCard.setExceptionRestriction(4,5,true));
        assertDoesNotThrow(()->patternCard.setExceptionRestriction(4,1,true));
        assertDoesNotThrow(()->patternCard.setExceptionRestriction(3,3,true));
    }

}
