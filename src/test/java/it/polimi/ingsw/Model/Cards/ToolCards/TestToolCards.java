package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Cards.ToolCards.*;
import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestToolCards {

    @Test
    public void toolCard1Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        for (Dice c : drawPool) {
            if (c.valueOf() == 1 || c.valueOf() == 6) {
                try {
                    c.setFace(3);
                } catch (InvalidFaceException e) {
                    e.printStackTrace();
                }
            }
        }
        Player player1 = new Player("Gamer1");
        player1.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();


        int dimension = drawPool.size();
        ToolCard toolCard = new ToolCard1();
        String commands = "1\nINC\n1\n3";
        Dice dice1 = drawPool.get(0);
        int value = dice1.valueOf();
        toolCard.useAbility(drawPool, roundTrack, diceBag, player1, playerTurns, commands);

        //funzionamento base
        assertEquals(dimension - 1, drawPool.size());
        assertEquals(dice1, windowFrame.getDice(1, 3));
        assertEquals(value, windowFrame.getDice(1, 3).valueOf() - 1);

        //verifica che non posso trasformare un 1 in 6
        Dice dice2;
        try {
            drawPool.get(0).setFace(1);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dice2 = drawPool.get(0);
        dimension = drawPool.size();
        String commands1 = "1\nDEC\n2\n2";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player1, playerTurns, commands1);
        assertEquals(dimension - 1, drawPool.size());
        assertEquals(dice2, windowFrame.getDice(2, 2));
        assertEquals(1, windowFrame.getDice(2, 2).valueOf());

        //verifica che non posso trasformare un 6 in 1
        Dice dice3;
        try {
            drawPool.get(0).setFace(6);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dice3 = drawPool.get(0);
        dimension = drawPool.size();
        String commands2 = "1\nINC\n2\n4";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player1, playerTurns, commands2);
        assertEquals(dimension - 1, drawPool.size());
        assertEquals(dice3, windowFrame.getDice(2, 4));
        assertEquals(6, windowFrame.getDice(2, 4).valueOf());

        //verifica che devo rispettare restrizione di numero
        Dice dice4;
        try {
            drawPool.get(0).setFace(4);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dice4 = drawPool.get(0);
        dimension = drawPool.size();
        String commands4 = "1\nINC\n3\n1";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player1, playerTurns, commands4);
        assertEquals(dimension, drawPool.size());
        assertEquals(null, windowFrame.getDice(3, 1));

        //verifica che devo rispettare restrizione di colore
        Dice dice5 = drawPool.get(0);
        dice5.setColor(Color.ANSI_BLUE);
        try {
            dice5.setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dimension = drawPool.size();
        String commands5 = "1\nINC\n3\n5";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player1, playerTurns, commands5);
        assertEquals(dimension, drawPool.size());
        assertEquals(null, windowFrame.getDice(2, 5));
    }

    @Test
    public void toolCard2Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();


        ToolCard toolCard = new ToolCard2();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        drawPool.get(1).setColor(Color.ANSI_GREEN);
        try {
            drawPool.get(0).setFace(2);
            drawPool.get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        String commands = new String();
        Dice dice = null;
        Dice dice1 = null;

        try {
            commands = "1\n1\n3\n";
            dice = move.chooseDiceFromDP(drawPool, commands);
            move.ordinaryMove(windowFrame, dice, drawPool, commands.substring(2));
            commands = "1\n2\n2\n";
            dice1 = move.chooseDiceFromDP(drawPool, commands);
            move.ordinaryMove(windowFrame, dice1, drawPool, commands.substring(2));
        } catch (InvalidNeighboursException | InvalidFirstMoveException | MismatchedRestrictionException | OccupiedCellException e) {
            e.printStackTrace();
        }

        int dimension = drawPool.size();

        //funzionamento base
        commands = "2\n2\n1\n2\n";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player, playerTurns, commands);
        assertEquals(dimension, drawPool.size());
        assertEquals(dice1, windowFrame.getDice(1, 2));


        //verifica che devo rispettare restrizione numero
        commands = "1\n2\n2\n3\n1\n2\n";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player, playerTurns, commands);
        assertEquals(dimension, drawPool.size());
        assertEquals(dice1, windowFrame.getDice(1, 2));

        //verifica che devo rispettare restrizione posizione
        commands = "1\n2\n3\n2\n1\n2\n";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player, playerTurns, commands);
        assertEquals(dimension, drawPool.size());
        assertEquals(dice1, windowFrame.getDice(1, 2));

    }


    @Test
    public void toolCard3Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();


        ToolCard toolCard = new ToolCard3();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        drawPool.get(1).setColor(Color.ANSI_GREEN);
        try {
            drawPool.get(0).setFace(2);
            drawPool.get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        String commands = new String();
        Dice dice = null;
        Dice dice1 = null;

        try {
            commands = "1\n1\n3\n";
            dice = move.chooseDiceFromDP(drawPool, commands);
            move.ordinaryMove(windowFrame, dice, drawPool, commands.substring(2));
            commands = "1\n2\n2\n";
            dice1 = move.chooseDiceFromDP(drawPool, commands);
            move.ordinaryMove(windowFrame, dice1, drawPool, commands.substring(2));
        } catch (InvalidNeighboursException | InvalidFirstMoveException | MismatchedRestrictionException | OccupiedCellException e) {
            e.printStackTrace();
        }

        int dimension = drawPool.size();

        //funzionamento base
        commands = "2\n2\n2\n3\n";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player, playerTurns, commands);
        assertEquals(dimension, drawPool.size());
        assertEquals(dice1, windowFrame.getDice(2, 3));


        //verifica che devo rispettare restrizione colore
        commands = "2\n3\n1\n2\n2\n3\n";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player, playerTurns, commands);
        assertEquals(dimension, drawPool.size());
        assertEquals(dice1, windowFrame.getDice(2, 3));

        //verifica che devo rispettare restrizione posizione
        commands = "2\n3\n4\n3\n2\n3\n";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player, playerTurns, commands);
        assertEquals(dimension, drawPool.size());
        assertEquals(dice1, windowFrame.getDice(2, 3));

    }

    @Test
    public void ToolCard4Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();


        ToolCard toolCard = new ToolCard4();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        drawPool.get(1).setColor(Color.ANSI_GREEN);
        try {
            drawPool.get(0).setFace(2);
            drawPool.get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        String commands = new String();
        Dice dice = null;
        Dice dice1 = null;

        try {
            commands = "1\n1\n3\n";
            dice = move.chooseDiceFromDP(drawPool, commands);
            move.ordinaryMove(windowFrame, dice, drawPool, commands.substring(2));
            commands = "1\n2\n2\n";
            dice1 = move.chooseDiceFromDP(drawPool, commands);
            move.ordinaryMove(windowFrame, dice1, drawPool, commands.substring(2));
        } catch (InvalidNeighboursException | InvalidFirstMoveException | MismatchedRestrictionException | OccupiedCellException e) {
            e.printStackTrace();
        }

        int dimension = drawPool.size();

        //Test funzionamento base
        commands = "1\n3\n3\n2\n2\n2\n4\n3\n";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player, playerTurns, commands);
    }

    @Test
    public void ToolCard5Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();

        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.get(0).setActionPerformed(ActionPerformed.DEFAULT);
        playerTurns.get(1).setActionPerformed(ActionPerformed.NOTHING);

        for (int i=0; i<10; i++) {
                roundTrack.add(new ArrayList<>());
            for (int j=0;j<2;j++) {
                roundTrack.get(i).add(diceBag.draw());
            }
        }

        ToolCard toolCard = new ToolCard5();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        try {
            drawPool.get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice diceDrawPool = drawPool.get(0);
        Dice diceRoundTrack = roundTrack.get(3).get(1);
        int dimension = drawPool.size();

        String commands = "1\n4\n2\n1\n3\n";
        //test funzionamento base
        toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands);

        assertEquals(diceRoundTrack,windowFrame.getDice(1,3));
        assertEquals(diceDrawPool, roundTrack.get(3).get(1));

    }

    @Test
    public void ToolCard6Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();

        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.get(0).setActionPerformed(ActionPerformed.DEFAULT);
        playerTurns.get(1).setActionPerformed(ActionPerformed.NOTHING);


        ToolCard toolCard = new ToolCard6();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        try {
            drawPool.get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice = drawPool.get(0);
        int dimension = drawPool.size();

        //funzionamento base
        String commands = "1\n1\n3\n";
        toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands);
        assertEquals(dimension-1, drawPool.size());


        //riposizionamento in riserva
        dimension = drawPool.size();
        drawPool.get(0).setColor(Color.ANSI_RED);
        try {
            drawPool.get(0).setFace(1);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        Dice dice2 = drawPool.get(0);
        commands = "1\n1\n2\n";
        toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands);
        assertEquals(dimension, drawPool.size());
        assertEquals(dice2,drawPool.get(drawPool.size()-1));
    }

    @Test
    public void ToolCard7Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();

        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.get(0).setActionPerformed(ActionPerformed.DEFAULT);
        playerTurns.get(1).setActionPerformed(ActionPerformed.NOTHING);


        ToolCard toolCard = new ToolCard7();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        try {
            drawPool.get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice = drawPool.get(0);
        int dimension = drawPool.size();
        String commands = "1\n1\n3\n";

        //test primo round
        assertThrows(WrongRoundException.class, ()->toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands));
        //test secondo round: 0 mosse effettuate
        playerTurns.get(0).setActionPerformed(ActionPerformed.COMPLETED);
        playerTurns.get(1).setActionPerformed(ActionPerformed.NOTHING);
        assertThrows(WrongRoundException.class, ()->toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands));
        //test secondo round: prima mossa effettuata

        playerTurns.get(0).setActionPerformed(ActionPerformed.COMPLETED);
        playerTurns.get(1).setActionPerformed(ActionPerformed.DEFAULT);
        toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands);
        assertEquals(dice,windowFrame.getDice(1,3));
    }

    @Test
    public void ToolCard8Test() throws WrongRoundException, InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();

        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.get(0).setActionPerformed(ActionPerformed.DEFAULT);
        playerTurns.get(1).setActionPerformed(ActionPerformed.NOTHING);


        ToolCard toolCard = new ToolCard8();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        try {
            drawPool.get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice = drawPool.get(0);
        int dimension = drawPool.size();
        String commands = "1\n1\n3\n";
        move.chooseDiceFromDP(drawPool,commands);
        move.ordinaryMove(windowFrame,dice,drawPool,commands.substring(2));

        //test funzionamento base
        int numOfTurns = playerTurns.size();
        commands = "1\n2\n2\n";
        toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands);
        assertEquals(numOfTurns-1, playerTurns.size());

        int count = 0;

        for (PlayerTurn c: playerTurns) {
            if (c.getPlayer().getName().equals(player.getName())) {
                count++;
            }
        }
        assertEquals(1, count);
    }

    @Test
    public void ToolCard9Test() throws InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();

        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.get(0).setActionPerformed(ActionPerformed.DEFAULT);
        playerTurns.get(1).setActionPerformed(ActionPerformed.NOTHING);


        ToolCard toolCard = new ToolCard9();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        try {
            drawPool.get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice = drawPool.get(0);
        int dimension = drawPool.size();
        String commands = "1\n1\n3\n";
        move.chooseDiceFromDP(drawPool,commands);
        move.ordinaryMove(windowFrame,dice,drawPool,commands.substring(2));

        Dice dice1 = drawPool.get(0);
        //test funzionamento base
        commands = "1\n4\n3\n";
        toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands);

        assertEquals(dice1, windowFrame.getDice(4,3));

    }

    @Test
    public void ToolCard10Test() throws InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        Move move = new Move();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();

        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.add(new PlayerTurn(player, drawPool));
        playerTurns.get(0).setActionPerformed(ActionPerformed.DEFAULT);
        playerTurns.get(1).setActionPerformed(ActionPerformed.NOTHING);


        ToolCard toolCard = new ToolCard10();
        drawPool.get(0).setColor(Color.ANSI_BLUE);
        try {
            drawPool.get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        int face = drawPool.get(0).valueOf();
        int dimension = drawPool.size();

        //test funzionamento base
        String commands = "1\n1\n3\n";
        toolCard.useAbility(drawPool,roundTrack,diceBag,player,playerTurns,commands);

        assertEquals(7-face, windowFrame.getDice(1,3).valueOf());

    }

    @Test
    public void toolCard11Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player1 = new Player("Gamer1");
        player1.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = null;
        Dice dice = new Dice(Color.ANSI_GREEN);
        try {
            dice.setFace(6);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        ToolCard toolCard = new ToolCard11();
        String commands = "1\n6\n1\n3";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player1, playerTurns, commands);

        //funzionamento base
        assertEquals(dice.getFace(), windowFrame.getDice(1, 3).getFace());
    }

    @Test
    public void toolCard12Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(1));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> drawPool = diceBag.draw(9);
        Player player1 = new Player("Gamer1");
        player1.setWindowFrame(windowFrame);
        ArrayList<ArrayList<Dice>> roundTrack = new ArrayList<>();
        Dice dice;
        for (int i=0; i<10; i++) {
            roundTrack.add(new ArrayList<>());
            for (int j=0;j<2;j++) {
                dice = diceBag.draw();
                if(dice.getColor().equals(Color.ANSI_BLUE)) dice.setColor(Color.ANSI_GREEN);
                roundTrack.get(i).add(dice);
            }
        }
        roundTrack.get(0).get(0).setColor(Color.ANSI_GREEN);
        dice = new Dice(Color.ANSI_GREEN);
        Dice dice1 = new Dice(Color.ANSI_GREEN);
        Dice dice2 = new Dice(Color.ANSI_BLUE);
        try {
            dice.setFace(3);
            dice1.setFace(2);
            dice2.setFace(4);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        try {
            windowFrame.setDice(3, 1, dice);
            windowFrame.setDice(2, 2, dice1);
            windowFrame.setDice(1, 2, dice2);
        } catch (MismatchedRestrictionException
                |InvalidNeighboursException
                |InvalidFirstMoveException
                |OccupiedCellException e) {
            e.printStackTrace();
        }

        ToolCard toolCard = new ToolCard12();
        String commands = "3\n1\n1\n3\n2\n2\n2\n4";
        toolCard.useAbility(drawPool, roundTrack, diceBag, player1, playerTurns, commands);

        //funzionamento base
        assertEquals(dice, windowFrame.getDice(1, 3));
        assertEquals(dice1, windowFrame.getDice(2, 4));
        assertEquals(dice2, windowFrame.getDice(1, 2));
    }

}
