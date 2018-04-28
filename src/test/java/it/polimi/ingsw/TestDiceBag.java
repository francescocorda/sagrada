package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDiceBag {
    @Test
    public void dimensionTest(){
        DiceBag diceBagProva=new DiceBag();
        assertEquals(90, diceBagProva.size());
        diceBagProva.draw();
        assertEquals(89, diceBagProva.size());
    }
}
