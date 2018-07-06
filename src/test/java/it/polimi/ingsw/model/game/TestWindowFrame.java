package it.polimi.ingsw.model.game;


import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.patterns.PatternDeck;
import it.polimi.ingsw.database.ParserManager;
import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;



class TestWindowFrame {

    private ParserManager pm = ParserManager.getParserManager();

    @Test
    void setDiceTest() {
        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.PURPLE);
        Dice dice2 = new Dice(Color.RED);
        try{
            dice.setFace(4);
            dice2.setFace(6);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        window.setPatternCard(pattern);
        try {
            window.setDice(1,1, dice);
        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | OccupiedCellException
                | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        //test funzionamento base setDice
        assertEquals(dice, window.getDice(1,1));

        //test inserimento posizione occupata
        assertThrows(OccupiedCellException.class, ()->window.setDice(1,1, new Dice(Color.YELLOW)));

        //test inserimento posizioni non valide
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(7,1, dice2));
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(3,10,dice2));
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(-1,10,dice2));
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(4,-4, dice2));

        //test di corretto funzionamento senza eccezioni
        assertDoesNotThrow(()->window.setDice(2,1,dice2));

        //test inserimento parametri nulli
        Integer a = null;
        assertThrows(NullPointerException.class, ()->window.setDice(2,a,dice2));
        assertThrows(NullPointerException.class, ()->window.setDice(a,3,dice2));

        Dice dice3 = null;
        assertThrows(NullPointerException.class, ()->window.setDice(1,1,dice3));
    }

    @Test
    void getDiceTest() {
        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.PURPLE);
        window.setPatternCard(pattern);

        try {
            dice.setFace(1);
            window.setDice(1,1, dice);
        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | OccupiedCellException
                | InvalidFirstMoveException
                | InvalidFaceException e) {
            e.printStackTrace();
        }
        //test funzionamento base setDice
        assertEquals(dice, window.getDice(1,1));

        //test lettura dado non presente
        assertEquals(null, window.getDice(3,1));

        //test lettura posizioni non valide
        assertThrows(IndexOutOfBoundsException.class, ()->window.getDice(7,1));
        assertThrows(IndexOutOfBoundsException.class, ()->window.getDice(3,10));
        assertThrows(IndexOutOfBoundsException.class, ()->window.getDice(-1,10));
        assertThrows(IndexOutOfBoundsException.class, ()->window.getDice(4,-4));

        //test di corretto funzionamento senza eccezioni
        assertDoesNotThrow(()->window.getDice(3,1));

        //test inserimento parametri nulli
        Integer a= null;
        assertThrows(NullPointerException.class, ()->window.getDice(2,a));
        assertThrows(NullPointerException.class, ()->window.getDice(a,3));
    }


    @Test
    void removeDiceTest() {
        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.PURPLE);
        window.setPatternCard(pattern);

        try {
            window.setDice(1,1, dice);
        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | InvalidFirstMoveException
                | OccupiedCellException e) {
            e.printStackTrace();
        }
        //test funzionamento base removeDice


        try {
            assertEquals(dice, window.removeDice(1,1));
        } catch (DiceNotFoundException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }


        //test rimozione di un dado non presente in posizione valida
        assertThrows(DiceNotFoundException.class, ()->window.removeDice(2,3));

        //versione alternativa al caso di dado non presente in posizione valida
        //assertEquals(null, window.removeDice(2,3));

        //test rimozione posizioni non valide
        assertThrows(IndexOutOfBoundsException.class, ()->window.removeDice(7,1));
        assertThrows(IndexOutOfBoundsException.class, ()->window.removeDice(3,10));
        assertThrows(IndexOutOfBoundsException.class, ()->window.removeDice(-1,10));
        assertThrows(IndexOutOfBoundsException.class, ()->window.removeDice(4,-4));

        //test di corretto funzionamento senza eccezioni
        try {
            window.setDice(3,1, dice);
        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | OccupiedCellException
                | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        assertDoesNotThrow(()->window.getDice(3,1));


        //test inserimento parametri nulli
        Integer a = null;
        assertThrows(NullPointerException.class, ()->window.removeDice(2,a));
        assertThrows(NullPointerException.class, ()->window.removeDice(a,3));
    }

    @Test
    void hasNeighboursTest() {
        WindowFrame window = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck(pm.getPatternDeck());
        PatternCard pattern = null;
        try {
            pattern = patternDeck.getPatternCard(9);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Dice dice1 = new Dice(Color.PURPLE);
        Dice dice2 = new Dice(Color.BLUE);
        Dice dice3 = new Dice(Color.RED);
        window.setPatternCard(pattern);

        try {
            dice1.setFace(1);
            window.setDice(4,1, dice1);
            dice2.setFace(2);
            window.setDice(3,2, dice2);
            dice3.setFace(3);
        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | InvalidFirstMoveException
                | OccupiedCellException
                | InvalidFaceException e) {
            e.printStackTrace();
        }
        //test funzionamento base hasNeighbours
        assertEquals(true, window.hasNeighbours(3,1));
        assertEquals(true, window.hasNeighbours(2,1));
        assertEquals(true, window.hasNeighbours(2,2));
        assertEquals(true, window.hasNeighbours(2,3));
        assertEquals(true, window.hasNeighbours(3,3));
        assertEquals(true, window.hasNeighbours(4,3));
        assertEquals(true, window.hasNeighbours(4,2));
        assertEquals(true, window.hasNeighbours(4,1));
        assertEquals(true, window.hasNeighbours(3,2));
        assertEquals(false, window.hasNeighbours(1,3));


        //test hasNeighbours posizioni non valide
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasNeighbours(7,1));
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasNeighbours(3,10));
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasNeighbours(-1,10));
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasNeighbours(4,-4));

        //test inserimento parametri nulli
        Integer a = null;
        assertThrows(NullPointerException.class, ()->window.hasNeighbours(2,a));
        assertThrows(NullPointerException.class, ()->window.hasNeighbours(a,3));
    }

    @Test
    void checkNeighboursRestrictionTest() {
        WindowFrame window = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck(pm.getPatternDeck());
        PatternCard pattern = null;
        try {
            pattern = patternDeck.getPatternCard(9);
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Dice dice1 = new Dice(Color.PURPLE);
        Dice dice2 = new Dice(Color.BLUE);
        Dice dice3 = new Dice(Color.RED);
        window.setPatternCard(pattern);

        try {
            dice1.setFace(1);
            window.setDice(4,1, dice1);
            dice2.setFace(2);
            window.setDice(3,2, dice2);
            dice3.setFace(3);
        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | InvalidFirstMoveException
                | OccupiedCellException
                | InvalidFaceException e) {
            e.printStackTrace();
        }
        //test funzionamento base checkNeighboursRestriction
        assertEquals(true, window.checkNeighboursRestriction(3,1, dice3));
        assertEquals(true, window.checkNeighboursRestriction(2,1, dice3));
        assertEquals(true, window.checkNeighboursRestriction(2,2, dice3));
        assertEquals(true, window.checkNeighboursRestriction(2,3, dice3));
        assertEquals(true, window.checkNeighboursRestriction(3,3, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,3, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,2, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,1, dice3));
        assertEquals(true, window.checkNeighboursRestriction(3,2, dice3));

        //test posizioni senza vicini
        assertEquals(true, window.checkNeighboursRestriction(2,4, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,4, dice3));
        assertEquals(true, window.checkNeighboursRestriction(1,3, dice3));

        //test dado stessa restrizione colore
        dice3.setColor(Color.BLUE);
        assertEquals(false, window.checkNeighboursRestriction(3,1, dice3));
        assertEquals(false, window.checkNeighboursRestriction(3,3, dice3));
        assertEquals(false, window.checkNeighboursRestriction(2,2, dice3));
        assertEquals(false, window.checkNeighboursRestriction(4,2, dice3));

        assertEquals(true, window.checkNeighboursRestriction(2,1, dice3));
        assertEquals(true, window.checkNeighboursRestriction(2,3, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,1, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,3, dice3));
        assertEquals(true, window.checkNeighboursRestriction(3,2, dice3));

        //test dado stessa restrizione faccia
        try {
            dice3.setFace(2);
            dice3.setColor(Color.GREEN);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        assertEquals(false, window.checkNeighboursRestriction(3,1, dice3));
        assertEquals(false, window.checkNeighboursRestriction(3,3, dice3));
        assertEquals(false, window.checkNeighboursRestriction(2,2, dice3));
        assertEquals(false, window.checkNeighboursRestriction(4,2, dice3));

        assertEquals(true, window.checkNeighboursRestriction(2,1, dice3));
        assertEquals(true, window.checkNeighboursRestriction(2,3, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,1, dice3));
        assertEquals(true, window.checkNeighboursRestriction(4,3, dice3));
        assertEquals(true, window.checkNeighboursRestriction(3,2, dice3));

        //test checkNeighboursRestriction posizioni non valide
        assertThrows(IndexOutOfBoundsException.class, ()->window.checkNeighboursRestriction(7,1, dice3));
        assertThrows(IndexOutOfBoundsException.class, ()->window.checkNeighboursRestriction(3,10, dice3));
        assertThrows(IndexOutOfBoundsException.class, ()->window.checkNeighboursRestriction(-1,10, dice3));
        assertThrows(IndexOutOfBoundsException.class, ()->window.checkNeighboursRestriction(4,-4, dice3));

        //test inserimento parametri nulli
        Integer a = null;
        assertThrows(NullPointerException.class, ()->window.checkNeighboursRestriction(2,a, dice3));
        assertThrows(NullPointerException.class, ()->window.checkNeighboursRestriction(a,3, dice3));
    }

    @Test
    void firstMoveTest() {

        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.PURPLE);
        window.setPatternCard(pattern);

        assertThrows(InvalidFirstMoveException.class, ()-> window.setDice(2,3,dice));
    }

    @Test
    void dumpTest(){
        WindowFrame window = new WindowFrame();
        PatternDeck deck = new PatternDeck(pm.getPatternDeck());
        //deck.createPatternDeck();
        Random rand=new Random();
        int index = rand.nextInt(deck.getPatternDeck().size());
        PatternCard pattern = deck.getPatternDeck().get(index);
        window.setPatternCard(pattern);
        window.dump();
    }


    @Test
    void firstGameDemoTest() {
        WindowFrame window = new WindowFrame();
        PatternDeck deck = new PatternDeck(pm.getPatternDeck());
        PatternCard pattern = deck.getPatternDeck().get(10);
        window.setPatternCard(pattern);
        window.dump();

        try {
            Dice dice1 = new Dice(Color.PURPLE);
            dice1.setFace(1);
            window.setDice(1,2, dice1);
            Dice dice2 = new Dice(Color.PURPLE);
            dice2.setFace(2);
            window.setDice(2,3, dice2);
            Dice dice3 = new Dice(Color.PURPLE);
            dice3.setFace(3);
            window.setDice(3,4, dice3);
            Dice dice4 = new Dice(Color.PURPLE);
            dice4.setFace(5);
            window.setDice(2,5, dice4);
        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | InvalidFirstMoveException
                | OccupiedCellException e) {
            e.printStackTrace();
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        window.dump();
    }

    @Test
    void sun_catcherTest(){  //Test di riempimento del pattern sun_catcher
        WindowFrame window = new WindowFrame();
        PatternDeck deck = new PatternDeck(pm.getPatternDeck());
        PatternCard card;
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> dices;
        Dice dice;
        try {
            card= deck.getPatternCard(4);
            window.setPatternCard(card);
            dices = diceBag.draw(4);
            window.dump();

            //test inserimento primo dado in posizione[4][1]
            dice = dices.remove(0);
            dice.setColor(Color.YELLOW);
            dice.setFace(1);
            Dice finalDice = dice;
            assertThrows(MismatchedRestrictionException.class, ()->window.setDice(4,1, finalDice));
            assertNotEquals(dice, window.getDice(4, 1));
            assertEquals(null, window.getDice(4, 1));

            //posizionamento 3 dadi
            window.setDice(1,1, dice);
            assertEquals(dice, window.getDice(1, 1));
            dice=dices.remove(0);
            dice.setColor(Color.GREEN);
            dice.setFace(4);
            window.setDice(2,2, dice);
            assertEquals(dice, window.getDice(2, 2));
            dice=dices.remove(0);
            dice.setColor(Color.GREEN);
            dice.setFace(5);
            window.setDice(3,3, dice);
            assertEquals(dice, window.getDice(3, 3));

            //set a true ExceptionRestriction in posizione [3][4]
            card.setExceptionRestriction(3, 4, true);
            Dice dice1= new Dice(Color.GREEN);
            dice1.setFace(4);
            assertThrows(InvalidNeighboursException.class, ()->window.setDice(3, 4, dice1));
            card.setExceptionRestriction(3, 4, true);

            Dice dice2= new Dice(Color.YELLOW);
            dice2.setFace(5);
            assertThrows(InvalidNeighboursException.class, ()->window.setDice(3, 4, dice2));
            card.setExceptionRestriction(3, 4, true);
            dice2.setFace(2);
            window.setDice(3, 4, dice2);
            assertEquals(dice2,window.getDice(3, 4));

            //set a true ExceptionPosition in posizione [1][5]
            card.setExceptionPosition(1,5, true);
            Dice dice3 = new Dice(Color.RED);
            dice3.setFace(5);
            assertThrows(MismatchedRestrictionException.class, ()->window.setDice(1, 5,dice3));

            card.setExceptionPosition(1,5, true);
            dice3.setColor(Color.YELLOW);
            dice3.setFace(5);
            window.setDice(1, 5, dice3);
            assertEquals(dice3,window.getDice(1, 5));

            //set a true ExceptionPosition in posizione [3][5]
            card.setExceptionPosition(3,5, true);
            Dice dice4 = new Dice(Color.YELLOW);
            dice4.setFace(3);
            assertThrows(InvalidNeighboursException.class, ()->window.setDice(3, 5,dice4));

            card.setExceptionPosition(3,5, true);
            dice4.setColor(Color.GREEN);
            dice4.setFace(2);
            assertThrows(InvalidNeighboursException.class, ()->window.setDice(3, 5,dice4));

            card.setExceptionPosition(3,5, true);
            dice4.setColor(Color.GREEN);
            dice4.setFace(4);
            window.setDice(3, 5, dice4);
            assertEquals(dice4,window.getDice(3, 5));

            //riempio tutta la window
            Dice dice5 = new Dice(Color.BLUE);
            dice5.setFace(3);
            window.setDice(1, 2, dice5);
            assertEquals(dice5,window.getDice(1, 2));
            Dice dice6 = new Dice(Color.RED);
            dice6.setFace(2);
            window.setDice(1, 3, dice6);
            assertEquals(dice6,window.getDice(1, 3));
            Dice dice7 = new Dice(Color.GREEN);
            dice7.setFace(3);
            window.setDice(1, 4, dice7);
            assertEquals(dice7,window.getDice(1, 4));
            Dice dice8 = new Dice(Color.BLUE);
            dice8.setFace(2);
            window.setDice(2, 1, dice8);
            assertEquals(dice8,window.getDice(2, 1));
            Dice dice9 = new Dice(Color.BLUE);
            dice9.setFace(1);
            window.setDice(2, 3, dice9);
            assertEquals(dice9,window.getDice(2, 3));
            Dice dice10 = new Dice(Color.RED);
            dice10.setFace(5);
            window.setDice(2, 4, dice10);
            assertEquals(dice10,window.getDice(2, 4));
            Dice dice11 = new Dice(Color.PURPLE);
            dice11.setFace(3);
            window.setDice(2, 5, dice11);
            assertEquals(dice11,window.getDice(2, 5));
            Dice dice12 = new Dice(Color.PURPLE);
            dice12.setFace(3);
            window.setDice(3, 1, dice12);
            assertEquals(dice12,window.getDice(3, 1));
            Dice dice13 = new Dice(Color.YELLOW);
            dice13.setFace(2);
            window.setDice(3, 2, dice13);
            assertEquals(dice13,window.getDice(3, 2));

            Dice dice14 = new Dice(Color.GREEN);
            dice14.setFace(1);
            window.setDice(4, 1, dice14);
            assertEquals(dice14,window.getDice(4, 1));
            Dice dice15 = new Dice(Color.PURPLE);
            dice15.setFace(3);
            window.setDice(4, 2, dice15);
            assertEquals(dice15,window.getDice(4, 2));
            Dice dice16 = new Dice(Color.BLUE);
            dice16.setFace(2);
            window.setDice(4, 3, dice16);
            assertEquals(dice16,window.getDice(4, 3));
            Dice dice17 = new Dice(Color.RED);
            dice17.setFace(3);
            window.setDice(4, 4, dice17);
            assertEquals(dice17,window.getDice(4, 4));
            Dice dice18 = new Dice(Color.PURPLE);
            dice18.setFace(1);
            window.setDice(4, 5, dice18);
            assertEquals(dice18,window.getDice(4, 5));
            Dice dice19 = new Dice(Color.PURPLE);
            dice19.setFace(1);

            //non si possono inserire più di 20 dadi
            assertThrows(OccupiedCellException.class,()->window.setDice(4, 5, dice19));


        } catch (MismatchedRestrictionException
                | InvalidNeighboursException
                | OccupiedCellException
                | InvalidFirstMoveException
                | NotValidInputException
                | InvalidFaceException e) {
            e.printStackTrace();
        }
        window.dump();
    }
}

