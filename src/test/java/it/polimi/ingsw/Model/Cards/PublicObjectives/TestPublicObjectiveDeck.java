package it.polimi.ingsw.Model.Cards.PublicObjectives;


import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.WindowFrame;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveDeck;
import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPublicObjectiveDeck {

    @Test
    void removePuOCTest(){
        PublicObjectiveDeck publicObjectiveDeck = new PublicObjectiveDeck();
        assertEquals(10, publicObjectiveDeck.size());
        assertThrows(IndexOutOfBoundsException.class, ()-> publicObjectiveDeck.removePuOC(10));

        assertDoesNotThrow(()-> publicObjectiveDeck.removePuOC(3));
        assertEquals(9, publicObjectiveDeck.size());
        assertThrows(IndexOutOfBoundsException.class, ()-> publicObjectiveDeck.removePuOC(-1));
        assertThrows(IndexOutOfBoundsException.class, ()-> publicObjectiveDeck.removePuOC(11));       //attenzione all'indice
    }                                                                                                                     //dobbiamo decidere la
    //convenzione

    @Test
    void getPuOCTest() {
        PublicObjectiveDeck publicObjectiveDeck = new PublicObjectiveDeck();
        publicObjectiveDeck.dump();

        try {
            assertEquals(5, publicObjectiveDeck.getPuOC(4).getID());
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    void sizeTest() {
        PublicObjectiveDeck deck = new PublicObjectiveDeck();
        assertEquals(10, deck.size());
        deck.removePuOC(2);
        assertEquals(9, deck.size());
    }

    @Test
    public void ZeroPointsTest() {
        PublicObjectiveDeck publicObjectiveDeck = new PublicObjectiveDeck();
        WindowFrame windowFrame = new WindowFrame();
            for (int j = 0; j< publicObjectiveDeck.size(); j++) {
                try {
                    assertEquals(0, publicObjectiveDeck.getPuOC(j).countScore(windowFrame));
                } catch (NotValidInputException e) {
                    e.printStackTrace();
                }
            }
    }

    @Test
    public void FullSunCatcherTest() {
        PublicObjectiveDeck publicObjectiveDeck = new PublicObjectiveDeck();
        WindowFrame window = new WindowFrame();
        PatternDeck deck = new PatternDeck();
        PatternCard card;
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> dices;
        try {
            card= deck.getPatternCard(5);
            window.setPatternCard(card);

            Dice dice1 = new Dice(Color.ANSI_PURPLE);
            dice1.setFace(1);
            window.setDice(1,1, dice1);
            Dice dice2 = new Dice(Color.ANSI_BLUE);
            dice2.setFace(3);
            window.setDice(1, 2, dice2);
            Dice dice3 = new Dice(Color.ANSI_RED);
            dice3.setFace(2);
            window.setDice(1, 3, dice3);
            Dice dice4 = new Dice(Color.ANSI_GREEN);
            dice4.setFace(3);
            window.setDice(1, 4, dice4);
            Dice dice5 = new Dice(Color.ANSI_YELLOW);
            dice5.setFace(5);
            window.setDice(1, 5, dice5);
            Dice dice6 = new Dice(Color.ANSI_YELLOW);
            dice6.setFace(2);
            window.setDice(2, 1, dice6);
            Dice dice7 = new Dice(Color.ANSI_GREEN);
            dice7.setFace(4);
            window.setDice(2,2, dice7);
            Dice dice8 = new Dice(Color.ANSI_BLUE);
            dice8.setFace(1);
            window.setDice(2, 3, dice8);
            Dice dice9 = new Dice(Color.ANSI_RED);
            dice9.setFace(5);
            window.setDice(2, 4, dice9);
            Dice dice10 = new Dice(Color.ANSI_PURPLE);
            dice10.setFace(3);
            window.setDice(2, 5, dice10);
            Dice dice11 = new Dice(Color.ANSI_PURPLE);
            dice11.setFace(3);
            window.setDice(3, 1, dice11);
            Dice dice12 = new Dice(Color.ANSI_YELLOW);
            dice12.setFace(2);
            window.setDice(3, 2, dice12);
            Dice dice13 = new Dice(Color.ANSI_GREEN);
            dice13.setFace(5);
            window.setDice(3,3, dice13);
            Dice dice14= new Dice(Color.ANSI_YELLOW);
            dice14.setFace(2);
            window.setDice(3, 4, dice14);
            Dice dice15 = new Dice(Color.ANSI_GREEN);
            dice15.setFace(4);
            window.setDice(3, 5, dice15);
            Dice dice16 = new Dice(Color.ANSI_GREEN);
            dice16.setFace(1);
            window.setDice(4, 1, dice16);
            Dice dice17 = new Dice(Color.ANSI_PURPLE);
            dice17.setFace(3);
            window.setDice(4, 2, dice17);
            Dice dice18 = new Dice(Color.ANSI_BLUE);
            dice18.setFace(2);
            window.setDice(4, 3, dice18);
            Dice dice19 = new Dice(Color.ANSI_RED);
            dice19.setFace(3);
            window.setDice(4, 4, dice19);
            Dice dice20 = new Dice(Color.ANSI_PURPLE);
            dice20.setFace(1);
            window.setDice(4, 5, dice20);
        } catch (NotValidInputException
                | InvalidNeighboursException
                | MismatchedRestrictionException
                | InvalidFaceException
                | InvalidFirstMoveException
                | OccupiedCellException e) {
            e.printStackTrace();
        }
        window.dump();

        try {
            assertEquals(12, publicObjectiveDeck.getPuOC(0).countScore(window));
            assertEquals(5, publicObjectiveDeck.getPuOC(1).countScore(window));
            assertEquals(5, publicObjectiveDeck.getPuOC(2).countScore(window));
            assertEquals(4, publicObjectiveDeck.getPuOC(3).countScore(window));
            assertEquals(8, publicObjectiveDeck.getPuOC(4).countScore(window));
            assertEquals(4, publicObjectiveDeck.getPuOC(5).countScore(window));
            assertEquals(0, publicObjectiveDeck.getPuOC(6).countScore(window));
            assertEquals(0, publicObjectiveDeck.getPuOC(7).countScore(window));
            assertEquals(12, publicObjectiveDeck.getPuOC(8).countScore(window));
            assertEquals(10, publicObjectiveDeck.getPuOC(9).countScore(window));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
    }
}
