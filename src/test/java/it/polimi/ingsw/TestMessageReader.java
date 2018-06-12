package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestMessageReader {

    @Test
    void allMethodsTest(){
        assertDoesNotThrow(()->new MessageReader("login/Username/Password/PizzoCalabro"));
        MessageReader messageReader = new MessageReader("login/Username/Password/PizzoCalabro");
        assertEquals(true, messageReader.hasNext());
        String message = messageReader.getNext();
        System.out.println(message);
        assertEquals("login", message);
        assertEquals(true, messageReader.hasNext());
        message = messageReader.getNext();
        System.out.println(message);
        assertEquals("Username", message);
        assertDoesNotThrow(()->assertEquals(true, messageReader.hasNext()));
        assertEquals(true, messageReader.hasNext());
        message = messageReader.getNext();
        System.out.println(message);
        assertEquals("Password", message);
        assertEquals(true, messageReader.hasNext());
        message = messageReader.getNext();
        System.out.println(message);
        assertEquals("PizzoCalabro", message);
        assertEquals(false, messageReader.hasNext());
        assertThrows(NullPointerException.class, ()->messageReader.getNext());
        assertThrows(NullPointerException.class, ()->new MessageReader(null));
    }
}
