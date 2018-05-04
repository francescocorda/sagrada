package it.polimi.ingsw;


import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;



public class TestWindowFrame {


    @Test
    public void setDiceTest() {
        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.ANSI_PURPLE);
        try{
            dice.setFace(4);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        Dice dice2 = new Dice(Color.ANSI_RED);
        try{
            dice2.setFace(6);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        window.setPatternCard(pattern);
        try {
            window.setDice(1,1, dice);
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        }
        //test funzionamento base setDice
        assertEquals(dice, window.getDice(1,1));

        //test inserimento posizioni non valide
        Dice finalDice = dice;
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(7,1, finalDice));
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(3,10,finalDice));
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(-1,10,finalDice));
        assertThrows(IndexOutOfBoundsException.class, ()->window.setDice(4,-4, finalDice));

        //test di corretto funzionamento senza eccezioni
        assertDoesNotThrow(()->window.setDice(2,1,dice2));

        //test inserimento parametri nulli
        Integer a = null;
        assertThrows(NullPointerException.class, ()->window.setDice(2,a,finalDice));
        assertThrows(NullPointerException.class, ()->window.setDice(a,3,finalDice));

        Dice dice3 = null;
        assertThrows(NullPointerException.class, ()->window.setDice(1,1,dice3));
    }

    @Test
    public void getDiceTest() {
        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.ANSI_PURPLE);
        window.setPatternCard(pattern);

        try {
            dice.setFace(1);
            window.setDice(1,1, dice);
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        } catch (InvalidFaceException e) {
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
    public void removeDiceTest() {
        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.ANSI_PURPLE);
        window.setPatternCard(pattern);

        try {
            window.setDice(1,1, dice);
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        }
        //test funzionamento base removeDice


        try {
            assertEquals(dice, window.removeDice(1,1));
        } catch (DiceNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
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
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        }
        assertDoesNotThrow(()->window.getDice(3,1));


        //test inserimento parametri nulli
        Integer a = null;
        assertThrows(NullPointerException.class, ()->window.removeDice(2,a));
        assertThrows(NullPointerException.class, ()->window.removeDice(a,3));
    }

    @Test
    public void hasValidNeighboursTest() {
        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice1 = new Dice(Color.ANSI_PURPLE);
        Dice dice2 = new Dice(Color.ANSI_YELLOW);
        Dice dice3 = new Dice(Color.ANSI_BLUE);
        window.setPatternCard(pattern);

        try {
            dice1.setFace(1);
            window.setDice(4,1, dice1);
            dice2.setFace(2);
            window.setDice(3,2, dice2);
            dice3.setFace(3);
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        //test funzionamento base hasNeighbours
        assertEquals(true, window.hasValidNeighbours(3,1,dice3));
        assertEquals(true, window.hasValidNeighbours(2,1,dice3));
        assertEquals(true, window.hasValidNeighbours(2,2,dice3));
        assertEquals(true, window.hasValidNeighbours(2,3,dice3));
        assertEquals(true, window.hasValidNeighbours(3,3,dice3));
        assertEquals(true, window.hasValidNeighbours(4,3,dice3));
        assertEquals(true, window.hasValidNeighbours(4,2,dice3));
        assertEquals(false, window.hasValidNeighbours(1,3,dice3));


        //test hasNeighbours posizioni non valide
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasValidNeighbours(7,1,dice3));
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasValidNeighbours(3,10,dice3));
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasValidNeighbours(-1,10,dice3));
        assertThrows(IndexOutOfBoundsException.class, ()->window.hasValidNeighbours(4,-4,dice3));

        //test di corretto funzionamento senza eccezioni
        try {
            window.setDice(4,3,dice3);
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        }
        assertDoesNotThrow(()->window.hasValidNeighbours(3,1,dice3));


        //test inserimento parametri nulli
        Integer a = null;
        assertThrows(NullPointerException.class, ()->window.hasValidNeighbours(2,a,dice3));
        assertThrows(NullPointerException.class, ()->window.hasValidNeighbours(a,3,dice3));
    }

    @Test
    void firstMoveTest() {

        WindowFrame window = new WindowFrame();
        PatternCard pattern = new PatternCard("default", 1);
        Dice dice = new Dice(Color.ANSI_PURPLE);
        window.setPatternCard(pattern);

        assertThrows(InvalidFirstMoveException.class, ()-> window.setDice(2,3,dice));
    }

    @Test
    public void dumpTest(){
        WindowFrame window = new WindowFrame();
        PatternDeck deck = new PatternDeck();
        //deck.createPatternDeck();
        Random rand=new Random();
        int index = rand.nextInt(deck.getPatternDeck().size());
        PatternCard pattern = deck.getPatternDeck().get(index);
        window.setPatternCard(pattern);
        window.dump();
    }


    @Test
    public void firstGameDemoTest() {
        WindowFrame window = new WindowFrame();
        PatternDeck deck = new PatternDeck();
        //deck.createPatternDeck();
        PatternCard pattern = deck.getPatternDeck().get(10);
        window.setPatternCard(pattern);
        window.dump();

        try {
            window.setDice(1,2, new Dice(Color.ANSI_RED));
            window.setDice(2,3, new Dice(Color.ANSI_RED));
            window.setDice(3,4, new Dice(Color.ANSI_RED));
            window.setDice(4,5, new Dice(Color.ANSI_RED));
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        }

        window.dump();
    }
}

