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

    @Test
    public void fullDimensionTest(){
        DiceBag diceBag=new DiceBag();
        for(int i=0; i<90; i++){
            diceBag.draw();
        }
        assertEquals(0, diceBag.size());
    }

    @Test
    public void colorsNumberTest(){
        DiceBag diceBag = new DiceBag();
        int red=0;
        int green=0;
        int yellow=0;
        int blue=0;
        int purple=0;

        for(Dice test: diceBag.dices){
            switch(test.getColor()){
                case ANSI_BLUE:
                    blue++;
                    break;
                case ANSI_PURPLE:
                    purple++;
                    break;
                case ANSI_RED:
                    red++;
                    break;
                case ANSI_GREEN:
                    green++;
                    break;
                case ANSI_YELLOW:
                    yellow++;
                    break;
            }
        }
        assertEquals(18, red);
        assertEquals(18, green);
        assertEquals(18, blue);
        assertEquals(18, yellow);
        assertEquals(18, purple);
    }

}
