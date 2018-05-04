package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPrivateObjectiveDeck {

    @Test
    public void removePrivateObjectiveCard(){
        PrivateObjectiveDeck privateObjectiveDeck = new PrivateObjectiveDeck();
        assertEquals(5, privateObjectiveDeck.getPrivateObjectiveDeck().size());
        assertThrows(IndexOutOfBoundsException.class, ()->privateObjectiveDeck.removePrivateObjectiveCard(6));

        assertDoesNotThrow(()->privateObjectiveDeck.removePrivateObjectiveCard(3));
        assertEquals(4, privateObjectiveDeck.getPrivateObjectiveDeck().size());
        assertThrows(IndexOutOfBoundsException.class, ()->privateObjectiveDeck.removePrivateObjectiveCard(-1));
        assertThrows(IndexOutOfBoundsException.class, ()->privateObjectiveDeck.removePrivateObjectiveCard(5));       //attenzione all'indice
    }                                                                                                                     //dobbiamo decidere la
                                                                                                                          //convenzione

    @Test
    public  void getPrivateObjectiveCardTest() {
        PrivateObjectiveDeck privateObjectiveDeck = new PrivateObjectiveDeck();
        privateObjectiveDeck.dump();

        try {
            assertEquals(5, privateObjectiveDeck.getPrivateObjectiveCard(4).getID());
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
    }
}
