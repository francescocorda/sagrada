package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        ArrayList<Dice> dices = diceBag.draw(diceBag.size());
        int red=0;
        int green=0;
        int yellow=0;
        int blue=0;
        int purple=0;

        for(Dice test: dices){
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

    @Test
    public void drawArrayTest(){
        DiceBag diceBag = new DiceBag();
        int fullSize = diceBag.size();
        int numberForTest = 7;
        ArrayList<Dice> drawPool= diceBag.draw(numberForTest);
        assertEquals(numberForTest, drawPool.size());
        assertEquals(fullSize-numberForTest, diceBag.size());
        assertThrows(IndexOutOfBoundsException.class, ()->{diceBag.draw(fullSize-numberForTest+1);});
        ArrayList<Dice> allTheotherDices = diceBag.draw(fullSize-numberForTest);
        for(int i=0; i<numberForTest; i++){
            System.out.println("Tested position "+i);
            assertFalse(allTheotherDices.contains(drawPool.get(i)));
        }
    }

   @Test
    public void drawtest(){
        DiceBag diceBag = new DiceBag();
        int fullSize = diceBag.size();
        Dice drawnDice;
        for(int i=0; i<fullSize; i++){
            System.out.println("Test number "+i);
            drawnDice = diceBag.draw();
            assertEquals(fullSize -(i+1), diceBag.size());
        }
       assertThrows(IndexOutOfBoundsException.class, ()->{final Dice dice = diceBag.draw();});
   }
}
