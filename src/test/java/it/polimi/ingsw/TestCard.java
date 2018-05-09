package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCard {
    Card p = new PatternCard("prova", 3);

    @Test
    void setIDTest() {
        p.setID(4);
        assertEquals(4,p.getID());
    }

    @Test
    void setNameTest() {
        assertDoesNotThrow(()->p.setName("ciao"));
        p.dump();
        assertEquals("ciao", p.getName());

    }

}
