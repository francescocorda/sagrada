package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

public class TestPatternDeck {
    @Test
    public void firstTest() {
        PatternDeck prova = new PatternDeck();

        prova.createPatternDeck();
        prova.printPatternDeck();
    }

}
