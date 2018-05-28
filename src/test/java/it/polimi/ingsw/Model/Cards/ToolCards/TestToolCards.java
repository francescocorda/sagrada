package it.polimi.ingsw.Model.Cards.ToolCards;

import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestToolCards {

    @Test
    public void toolCard1Test() throws WrongRoundException, InvalidNeighboursException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        DiceBag diceBag = new DiceBag();
        ArrayList<Dice> draftPool = diceBag.draw(9);
        for (Dice c : draftPool) {
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
        RoundTrack roundTrack = new RoundTrack();
        int dimension = draftPool.size();
        ToolCard toolCard = new ToolCard1();
        ArrayList<String> commands = new ArrayList<>();
        commands.add("1"); commands.add("INC");
        Dice dice1 = draftPool.get(0);
        int value = dice1.valueOf();
        Dice dice2 = null;
        try {
            dice2 = toolCard.useAbility(draftPool, roundTrack, diceBag, player1, playerTurns, commands);
        } catch (DiceNotFoundException | InvalidFaceException | MismatchedRestrictionException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }

        //funzionamento base
        assertEquals(dimension-1, draftPool.size());
        assertEquals(value, dice2.valueOf() - 1);

        //verifica che non posso trasformare un 1 in 6
        try {
            draftPool.get(0).setFace(1);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dimension = draftPool.size();
        commands.add("1"); commands.add("DEC");
        Dice dice4 = draftPool.get(0);
        assertThrows(InvalidFaceException.class,()-> toolCard.useAbility(draftPool, roundTrack, diceBag, player1, playerTurns, commands));
        assertEquals(dimension, draftPool.size());
        assertEquals(1, dice4.valueOf());

        //verifica che non posso trasformare un 6 in 1;
        try {
            draftPool.get(0).setFace(6);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dimension = draftPool.size();
        commands.add("1");commands.add("INC");

        assertThrows(InvalidFaceException.class,()-> toolCard.useAbility(draftPool, roundTrack, diceBag, player1, playerTurns, commands));
        assertEquals(dimension , draftPool.size());
        assertEquals(6, dice4.valueOf());
    }

    @Test
    public void toolCard2Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);

        Player player = new Player("player");
        player.setWindowFrame(windowFrame);

        playerTurns.add(new PlayerTurn(player, table));
        playerTurns.get(0).setPlayerTurns(playerTurns);

        Move move1 = new Move(table.getDraftPool(), player, playerTurns);
        Move move2 = new Move(table.getDraftPool(), player, playerTurns);

        playerTurns.get(0).addMove(move1);
        playerTurns.get(0).addMove(move2);
        SpecialMove specialMove = new SpecialMove(table.getDraftPool(), player, table.getRoundTrack(), table.getDiceBag(), playerTurns);
        specialMove.setToolCard(new ToolCard2());
        playerTurns.get(0).addMove(specialMove);
        table.getDraftPool().get(0).setColor(Color.ANSI_BLUE);
        table.getDraftPool().get(1).setColor(Color.ANSI_GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice1 = table.getDraftPool().get(1);
        ArrayList<String> commands = new ArrayList<>();
        try {
            commands.add("1"); commands.add("1"); commands.add("3");
            move1.performMove(commands);
            commands.add("1"); commands.add("2"); commands.add("2");
            move2.performMove(commands);
            windowFrame.dump();
        } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | DiceNotFoundException | InvalidFirstMoveException | InvalidFaceException e) {
            e.printStackTrace();
        }


        int dimension = table.getDraftPool().size();

        //funzionamento base
        commands.add("WINDOW");commands.add("2"); commands.add("2"); commands.add("1"); commands.add("2");
        try {
            specialMove.performMove(commands);
            windowFrame.dump();
        } catch (DiceNotFoundException | InvalidFaceException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException | InvalidNeighboursException e) {
            e.printStackTrace();
        }
        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(dice1, windowFrame.getDice(1, 2));


        //verifica che devo rispettare restrizione numero
        commands.add("WINDOW");commands.add("1"); commands.add("2"); commands.add("2"); commands.add("3");
        playerTurns.get(0).addMove(specialMove);

        try {
            specialMove.performMove(commands);
        } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException | InvalidFaceException | DiceNotFoundException e) {
            e.printStackTrace();
        }
        windowFrame.dump();

        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(dice1, windowFrame.getDice(1, 2));
        assertEquals(false, windowFrame.getPatternCard().getExceptionRestriction(1,2));
        assertEquals(false, windowFrame.getPatternCard().getExceptionPosition(1,2));

        //verifica che devo rispettare restrizione posizione
        commands.add("WINDOW");commands.add("1"); commands.add("2"); commands.add("3"); commands.add("2");
        playerTurns.get(0).addMove(specialMove);
        try {
            specialMove.performMove(commands);
        } catch (DiceNotFoundException | InvalidFaceException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException | InvalidNeighboursException e) {
            e.printStackTrace();
        }
        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(dice1, windowFrame.getDice(1, 2));
        assertEquals(false, windowFrame.getPatternCard().getExceptionRestriction(1,2));
        assertEquals(false, windowFrame.getPatternCard().getExceptionPosition(1,2));
    }


    @Test
    public void toolCard3Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        Move move1 = new Move(table.getDraftPool(), player, playerTurns);
        Move move2 = new Move(table.getDraftPool(), player, playerTurns);
        SpecialMove move3 = new SpecialMove(table.getDraftPool(),player, table.getRoundTrack(), table.getDiceBag(),playerTurns);
        playerTurns.add(new PlayerTurn(player, table));
        playerTurns.get(0).setPlayerTurns(playerTurns);
        playerTurns.get(0).addMove(move1);
        playerTurns.get(0).addMove(move2);

        ToolCard toolCard = new ToolCard3();
        move3.setToolCard(toolCard);
        table.getDraftPool().get(0).setColor(Color.ANSI_BLUE);
        table.getDraftPool().get(1).setColor(Color.ANSI_GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice1 = table.getDraftPool().get(1);
        ArrayList<String> commands = new ArrayList<>();
        try {
            commands.add("1"); commands.add("1"); commands.add("3");
            move1.performMove(commands);
            commands.add("1"); commands.add("2"); commands.add("2");
            move2.performMove(commands);
            windowFrame.dump();
        } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | DiceNotFoundException | InvalidFirstMoveException | InvalidFaceException e) {
            e.printStackTrace();
        }
        int dimension = table.getDraftPool().size();

        //funzionamento base
        commands.add("WINDOW");commands.add("2"); commands.add("2"); commands.add("2"); commands.add("3");
        try {
            move3.performMove(commands);
            windowFrame.dump();
        } catch (DiceNotFoundException | InvalidFaceException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException | InvalidNeighboursException e) {
            e.printStackTrace();
        }
        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(dice1, windowFrame.getDice(2, 3));


        //verifica che devo rispettare restrizione colore
        commands.add("WINDOW");commands.add("2"); commands.add("3"); commands.add("1"); commands.add("2");
        playerTurns.get(0).addMove(move3);
        try {
            move3.performMove(commands);
            windowFrame.dump();
        } catch (DiceNotFoundException | InvalidFaceException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException | InvalidNeighboursException e) {
            e.printStackTrace();
        }
        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(dice1, windowFrame.getDice(2, 3));

        //verifica che devo rispettare restrizione posizione
        commands.add("WINDOW");commands.add("2"); commands.add("3"); commands.add("4"); commands.add("3");
        playerTurns.get(0).addMove(move3);
        try {
            move3.performMove(commands);
            windowFrame.dump();
        } catch (DiceNotFoundException | InvalidFaceException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException | InvalidNeighboursException e) {
            e.printStackTrace();
        }
        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(dice1, windowFrame.getDice(2, 3));

    }

    @Test
    public void ToolCard4Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);

        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        Move move1 = new Move(table.getDraftPool(), player, playerTurns);
        Move move2 = new Move(table.getDraftPool(), player, playerTurns);
        SpecialMove move3 = new SpecialMove(table.getDraftPool(),player, table.getRoundTrack(), table.getDiceBag(),playerTurns);
        SpecialMove move4 = new SpecialMove(table.getDraftPool(),player, table.getRoundTrack(), table.getDiceBag(),playerTurns);
        playerTurns.add(new PlayerTurn(player, table));
        playerTurns.get(0).setPlayerTurns(playerTurns);
        playerTurns.get(0).addMove(move1);
        playerTurns.get(0).addMove(move2);

        ToolCard toolCard = new ToolCard4();
        move3.setToolCard(toolCard);
        move4.setToolCard(toolCard);
        table.getDraftPool().get(0).setColor(Color.ANSI_BLUE);
        table.getDraftPool().get(1).setColor(Color.ANSI_GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice1 = table.getDraftPool().get(0);
        Dice dice2 = table.getDraftPool().get(1);

        ArrayList<String> commands = new ArrayList<>();
        try {
            commands.add("1"); commands.add("1"); commands.add("3");
            move1.performMove(commands);
            commands.add("1"); commands.add("2"); commands.add("2");
            move2.performMove(commands);
            windowFrame.dump();
        } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | DiceNotFoundException | InvalidFirstMoveException | InvalidFaceException e) {
            e.printStackTrace();
        }

        //Test funzionamento base
        commands.add("WINDOW");commands.add("1"); commands.add("3"); commands.add("3"); commands.add("2");
        commands.add("WINDOW");commands.add("2"); commands.add("2"); commands.add("4"); commands.add("3");

        try {
            move3.performMove(commands);
            windowFrame.dump();
            move4.performMove(commands);
            windowFrame.dump();
        } catch (InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | MismatchedRestrictionException | DiceNotFoundException | InvalidFaceException e) {
            e.printStackTrace();
        }

        assertEquals(dice1, windowFrame.getDice(3,2));
        assertEquals(dice2, windowFrame.getDice(4,3));
    }

    @Test
    public void ToolCard5Test() throws WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        Move move1 = new Move(table.getDraftPool(), player, playerTurns);
        Move move2 = new Move(table.getDraftPool(), player, playerTurns);
        SpecialMove move3 = new SpecialMove(table.getDraftPool(),player, table.getRoundTrack(), table.getDiceBag(),playerTurns);
        playerTurns.add(new PlayerTurn(player, table));
        playerTurns.get(0).setPlayerTurns(playerTurns);
        playerTurns.get(0).addMove(move1);
        playerTurns.get(0).addMove(move2);
        playerTurns.get(0).addMove(move3);


        for (int i=0; i<10; i++) {
            for (int j=0; j<2; j++) {
                table.getRoundTrack().getRoundDices(i).add(table.getDiceBag().draw());
            }
        }

        ToolCard toolCard = new ToolCard5();
        move3.setToolCard(toolCard);
        table.getDraftPool().get(0).setColor(Color.ANSI_BLUE);
        try {
            table.getDraftPool().get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice diceDrawPool = table.getDraftPool().get(0);
        Dice diceRoundTrack = table.getRoundTrack().getRoundDices(3).get(1);

        ArrayList<String> commands = new ArrayList<>();

        //Test funzionamento base
        commands.add("DRAFTPOOL");commands.add("1"); commands.add("4"); commands.add("2");
        commands.add("1"); commands.add("3");
        try {
            move3.performMove(commands);
            windowFrame.dump();
        } catch (DiceNotFoundException | InvalidFaceException | OccupiedCellException | MismatchedRestrictionException | InvalidFirstMoveException | InvalidNeighboursException e) {
            e.printStackTrace();
        }

        assertEquals(diceRoundTrack,windowFrame.getDice(1,3));
        assertEquals(diceDrawPool, table.getRoundTrack().getRoundDices(3).get(1));
    }

    @Test
    public void ToolCard6Test() {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);
        Player player = new Player("player");
        player.setWindowFrame(windowFrame);
        Move move1 = new Move(table.getDraftPool(), player, playerTurns);
        SpecialMove move2 = new SpecialMove(table.getDraftPool(),player, table.getRoundTrack(), table.getDiceBag(),playerTurns);
        SpecialMove move3 = new SpecialMove(table.getDraftPool(),player, table.getRoundTrack(), table.getDiceBag(),playerTurns);
        playerTurns.add(new PlayerTurn(player, table));
        playerTurns.get(0).setPlayerTurns(playerTurns);
        playerTurns.get(0).addMove(move1);
        playerTurns.get(0).addMove(move2);
        playerTurns.get(0).addMove(move3);

        ToolCard toolCard = new ToolCard6();
        ToolCard toolCard1 = new ToolCard6();
        move2.setToolCard(toolCard);
        move3.setToolCard(toolCard1);

        table.getDraftPool().get(0).setColor(Color.ANSI_GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        Dice dice = table.getDraftPool().get(0);
        int dimension = table.getDraftPool().size();

        for (Dice d: table.getDraftPool()) {
            System.out.print(d);
        }
        System.out.print("\n\n");

        //funzionamento base
        ArrayList<String> commands = new ArrayList<>();
        try {
            commands.add("DRAFTPOOL");commands.add("1");
            move2.performMove(commands);
            commands.add("4"); commands.add("2");
            move2.performMove(commands);
            windowFrame.dump();
            for (Dice d: table.getDraftPool()) {
                System.out.print(d);
            }
            System.out.print("\n\n");
        } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | DiceNotFoundException | InvalidFirstMoveException | InvalidFaceException | WrongRoundException e) {
            e.printStackTrace();
        }
        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(dice, windowFrame.getDice(4,2));

        //riposizionamento in riserva
        dimension = table.getDraftPool().size();
        try {
            table.getDraftPool().get(0).setFace(1);
            table.getDraftPool().get(0).setColor(Color.ANSI_PURPLE);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        Dice dice2 = table.getDraftPool().get(0);
        for (Dice d: table.getDraftPool()) {
            System.out.print(d);
        }
        System.out.print("\n\n");
        windowFrame.dump();
        System.out.println(commands.size());
        for (String s: commands) {
            System.out.println(s);
        }
        try {
            commands.add("DRAFTPOOL");commands.add("1");
            move3.performMove(commands);
            commands.add("3"); commands.add("3");
            move3.performMove(commands);
        } catch (InvalidNeighboursException | OccupiedCellException | MismatchedRestrictionException | DiceNotFoundException | InvalidFirstMoveException | InvalidFaceException | WrongRoundException e) {
            e.printStackTrace();
        }

        for (Dice d: table.getDraftPool()) {
            System.out.print(d);
        }
        System.out.print("\n\n");

        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(dice2 , table.getDraftPool().get(0));
    }

    @Test
    public void ToolCard7Test() throws WrongRoundException, InvalidFaceException, InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, DiceNotFoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);
        Player player1 = new Player("Gamer1");
        Player player2 = new Player("Gamer2");
        player1.setWindowFrame(windowFrame);
        playerTurns.add(new PlayerTurn(player1, table));
        playerTurns.add(new PlayerTurn(player2, table));
        ArrayList<Move> moves = new ArrayList<>();
        SpecialMove specialMove1 = new SpecialMove(table.getDraftPool(), player1, table.getRoundTrack(), table.getDiceBag(), playerTurns);
        moves.add(specialMove1);
        playerTurns.get(0).setMoves(moves);
        ToolCard toolCard = new ToolCard7();
        playerTurns.get(0).getMoves().get(0).setToolCard(toolCard);
        ArrayList<String> commands = new ArrayList<>();
        Dice dice = new Dice(Color.ANSI_BLUE);
        dice.setFace(3);
        windowFrame.setDice(4, 3, dice);

        //test funzionamento base
        //commands.add("NULL");
        int dim = table.getDraftPool().size();
        playerTurns.get(0).getMoves().get(0).performMove(commands);
        assertEquals(dim, table.getDraftPool().size());
        commands.add("DRAFTPOOL"); commands.add("1"); commands.add("3"); commands.add("2");
        playerTurns.get(0).getMoves().get(0).performMove(commands);
        assertNotEquals(null, windowFrame.getDice(3, 2));

        //test chiamata alla ToolCard durante il primo turno
        playerTurns.add(new PlayerTurn(player2, table));
        playerTurns.add(new PlayerTurn(player1, table));
        ToolCard toolCard1 = new ToolCard7();
        moves.add(specialMove1);
        moves.get(0).setToolCard(toolCard1);
        //commands.add("NULL");
        assertThrows(WrongRoundException.class, ()->playerTurns.get(0).getMoves().get(0).performMove(commands));

        //test chiamata alla ToolCard durante il secondo turno, prima di scegliere il primo dado
        playerTurns.remove(3);
        playerTurns.remove(2);
        moves.add(0, new Move(table.getDraftPool(), player1, playerTurns));
        //commands.add("NULL");
        assertThrows(WrongRoundException.class, ()->playerTurns.get(0).getMoves().get(1).performMove(commands));

    }

    @Test
    public void ToolCard8Test() throws WrongRoundException, InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);
        Player player1 = new Player("Gamer1");
        Player player2 = new Player("Gamer2");
        player1.setWindowFrame(windowFrame);
        playerTurns.add(new PlayerTurn(player1, table));
        playerTurns.add(new PlayerTurn(player2, table));
        playerTurns.add(new PlayerTurn(player2, table));
        playerTurns.add(new PlayerTurn(player1, table));
        for(int i=0; i<4; i++){
            ArrayList<Move> moves = new ArrayList<>();
            if(i==0 || i==3){
                moves.add(new Move(table.getDraftPool(), player1, playerTurns));
                SpecialMove specialMove1 = new SpecialMove(table.getDraftPool(), player1, table.getRoundTrack(),table.getDiceBag(), playerTurns);
                moves.add(specialMove1);
                playerTurns.get(0).setMoves(moves);
            } else {
                moves.add(new Move(table.getDraftPool(), player2, playerTurns));
                SpecialMove specialMove2 = new SpecialMove(table.getDraftPool(), player2, table.getRoundTrack(), table.getDiceBag(), playerTurns);
                moves.add(specialMove2);
                playerTurns.get(0).setMoves(moves);
            }
        }
        ToolCard toolCard = new ToolCard8();
        playerTurns.get(0).getMoves().get(1).setToolCard(toolCard);
        ArrayList<String> commands = new ArrayList<>();
        commands.add("DRAFTPOOL"); commands.add("1"); commands.add("1"); commands.add("3");

        //lancia eccezione se non ho ancora piazzato nessun dado
        assertThrows(WrongRoundException.class, ()->{playerTurns.get(0).getMoves().get(1).performMove(commands);});

        //funzionamento base
        playerTurns.get(0).getMoves().remove(0);
        ArrayList<String> commands1 = new ArrayList<>();
        commands1.add("DRAFTPOOL"); commands1.add("1"); commands1.add("1"); commands1.add("3");
        try {
            playerTurns.get(0).getMoves().get(0).performMove(commands1);
        } catch (DiceNotFoundException | InvalidFaceException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, windowFrame.getDice(1,3));

        //lancia eccezione se sono la mio secondo turno
        ArrayList moves = new ArrayList<>();
        moves.add(new Move(table.getDraftPool(), player1, playerTurns));
        SpecialMove specialMove1 = new SpecialMove(table.getDraftPool(), player2, table.getRoundTrack(), table.getDiceBag(), playerTurns);
        moves.add(specialMove1);
        playerTurns.get(0).setMoves(moves);
        playerTurns.get(0).getMoves().get(1).setToolCard(toolCard);
        ArrayList<String> commands3 = new ArrayList<>();
        commands3.add("DRAFTPOOL"); commands3.add("1");
        assertThrows(WrongRoundException.class, ()->{playerTurns.get(0).getMoves().get(1).performMove(commands3);});
    }

    @Test
    public void ToolCard9Test() throws InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        Dice dice = new Dice(Color.ANSI_BLUE);
        try {
            dice.setFace(2);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        windowFrame.setDice(1,2, dice);
        Table table = new Table();
        table.setDraftPool(9);
        Player player1 = new Player("Gamer1");
        player1.setWindowFrame(windowFrame);
        playerTurns.add(new PlayerTurn(player1, table));
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move(table.getDraftPool(), player1, playerTurns));
        SpecialMove specialMove1 = new SpecialMove(table.getDraftPool(), player1, table.getRoundTrack(), table.getDiceBag(), playerTurns);
        moves.add(specialMove1);
        playerTurns.get(0).setMoves(moves);
        ToolCard toolCard = new ToolCard9();
        playerTurns.get(0).getMoves().get(1).setToolCard(toolCard);
        ArrayList<String> commands = new ArrayList<>();

        //test funzionamento base
        commands.add("DRAFTPOOL");commands.add("1");commands.add("3");commands.add("4");
        try {
            playerTurns.get(0).getMoves().get(1).performMove(commands);
        } catch (DiceNotFoundException | InvalidFaceException e) {
            e.printStackTrace();
        }
        assertNotEquals(null, windowFrame.getDice(3, 4));
    }

    @Test
    public void ToolCard10Test() throws InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        Dice dice = new Dice(Color.ANSI_BLUE);
        try {
            dice.setFace(2);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        windowFrame.setDice(1,2, dice);
        Table table = new Table();
        table.setDraftPool(9);
        Player player1 = new Player("Gamer1");
        player1.setWindowFrame(windowFrame);
        playerTurns.add(new PlayerTurn(player1, table));
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move(table.getDraftPool(), player1, playerTurns));
        SpecialMove specialMove1 = new SpecialMove(table.getDraftPool(), player1, table.getRoundTrack(), table.getDiceBag(), playerTurns);
        moves.add(specialMove1);
        playerTurns.get(0).setMoves(moves);
        ToolCard toolCard = new ToolCard10();
        playerTurns.get(0).getMoves().get(1).setToolCard(toolCard);
        ArrayList<String> commands = new ArrayList<>();

        //test funzionamento base
        try {
            table.getDraftPool().get(0).setFace(2);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        commands.add("DRAFTPOOL"); commands.add("1"); commands.add("2"); commands.add("3");
        try {
            playerTurns.get(0).getMoves().get(1).performMove(commands);
        } catch (DiceNotFoundException | InvalidFaceException e) {
            e.printStackTrace();
        }
        assertEquals("\u2684", windowFrame.getDice(2, 3).getFace());

        //test posizionamento senza vicini
        int dim = table.getDraftPool().size();
        specialMove1.setToolCard(toolCard);
        playerTurns.get(0).getMoves().add(specialMove1);
        commands.add("DRAFTPOOL"); commands.add("1"); commands.add("4"); commands.add("3");
        assertDoesNotThrow(()->playerTurns.get(0).getMoves().get(1).performMove(commands));
        assertEquals(dim, table.getDraftPool().size());
    }

    @Test
    public void toolCard11Test() throws WrongRoundException, InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, InvalidFaceException, DiceNotFoundException {
        WindowFrame windowFrame = new WindowFrame();
        Dice dice = new Dice(Color.ANSI_BLUE);
        try {
            dice.setFace(2);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        PatternDeck patternDeck = new PatternDeck();
        ArrayList<PlayerTurn> playerTurns = new ArrayList<PlayerTurn>();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        windowFrame.setDice(1,2, dice);
        Table table = new Table();
        table.setDraftPool(9);
        Player player1 = new Player("Gamer1");
        player1.setWindowFrame(windowFrame);
        playerTurns.add(new PlayerTurn(player1, table));
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move(table.getDraftPool(), player1, playerTurns));
        SpecialMove specialMove1 = new SpecialMove(table.getDraftPool(), player1, table.getRoundTrack(), table.getDiceBag(), playerTurns);
        moves.add(specialMove1);
        playerTurns.get(0).setMoves(moves);
        ToolCard toolCard = new ToolCard11();
        playerTurns.get(0).getMoves().get(1).setToolCard(toolCard);
        ArrayList<String> commands = new ArrayList<>();

        //test funzionamento base
        commands.add("DRAFTPOOL"); commands.add("1");
        playerTurns.get(0).getMoves().get(1).performMove(commands);
        /*commands.add("NULL");*/ commands.add("5"); commands.add("2"); commands.add("3");
        playerTurns.get(0).getMoves().get(1).performMove(commands);
        assertEquals("\u2684", windowFrame.getDice(2, 3).getFace());

        //test posizionamento senza vicini
        int dim = table.getDraftPool().size();
        playerTurns.get(0).getMoves().add(specialMove1);
        ToolCard toolCard1 = new ToolCard11();
        playerTurns.get(0).getMoves().get(1).setToolCard(toolCard1);
        commands.add("DRAFTPOOL"); commands.add("1");
        playerTurns.get(0).getMoves().get(1).performMove(commands);
        /*commands.add("NULL");*/ commands.add("5"); commands.add("4"); commands.add("2");
        playerTurns.get(0).getMoves().get(1).performMove(commands);
        assertEquals(null, windowFrame.getDice(4,2));
        assertEquals(dim, table.getDraftPool().size());

    }

    @Test
    public void toolCard12Test() throws InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException, DiceNotFoundException, InvalidFaceException, WrongRoundException {
        WindowFrame windowFrame = new WindowFrame();
        PatternDeck patternDeck = new PatternDeck();
        try {
            windowFrame.setPatternCard(patternDeck.getPatternCard(0));
        } catch (NotValidInputException e) {
            e.printStackTrace();
        }
        Dice dice1 = new Dice(Color.ANSI_BLUE);
        Dice dice2 = new Dice(Color.ANSI_BLUE);
        try {
            dice1.setFace(2);
            dice2.setFace(5);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        Table table = new Table();
        table.setDraftPool(9);
        windowFrame.setDice(1,2, dice1);
        windowFrame.setDice(2,3, dice2);
        for(int i=0; i<10; i++){
            ArrayList<Dice> dices = new ArrayList<>();
            for(int j=0; j<2; j++){
                Dice dice = table.getDiceBag().draw();
                if(dice.getColor().equals(Color.ANSI_RED)) dice.setColor(Color.ANSI_BLUE);
                dices.add(dice);
            }
            table.getRoundTrack().setRoundDices(dices, i);
        }
        Dice dice = new Dice(Color.ANSI_BLUE);
        table.getRoundTrack().getRoundDices(9).add(dice);  //in the RT there is a dice blue but not red dices
        ArrayList<Dice> draftPool= table.getDraftPool();
        draftPool= table.getDiceBag().draw(1);
        Player player1 = new Player("Gamer1");
        player1.setWindowFrame(windowFrame);

        ArrayList<PlayerTurn> playerTurns = new ArrayList<>();
        playerTurns.add(new PlayerTurn(player1, table));
        ArrayList<Move> moves = new ArrayList<>();
        moves.add(new Move(table.getDraftPool(), player1, playerTurns));
        SpecialMove specialMove1 = new SpecialMove(table.getDraftPool(), player1, table.getRoundTrack(), table.getDiceBag(), playerTurns);
        moves.add(specialMove1);
        ToolCard toolCard = new ToolCard12();
        moves.get(1).setToolCard(toolCard);
        playerTurns.get(0).setMoves(moves);
        ArrayList<String> commands = new ArrayList<>();

        //Test funzionamento base
        commands.add("WINDOW"); commands.add("1"); commands.add("2"); commands.add("3"); commands.add("2");
        playerTurns.get(0).getMoves().get(1).performMove(commands);
        assertEquals("\u2681", windowFrame.getDice(3, 2).getFace());
        playerTurns.get(0).getMoves().add(specialMove1);
        commands.add("WINDOW"); commands.add("2"); commands.add("3"); commands.add("4"); commands.add("3");
        playerTurns.get(0).getMoves().get(1).performMove(commands);
        assertEquals("\u2684", windowFrame.getDice(4, 3).getFace());

        //spostamento di dado col colore non presente nel RT
        Dice dice3 = new Dice(Color.ANSI_RED);
        dice3.setFace(5);
        windowFrame.setDice(2, 3, dice3);
        ToolCard toolCard1 = new ToolCard12();
        specialMove1.setToolCard(toolCard1);
        playerTurns.get(0).getMoves().add(specialMove1);
        commands.add("WINDOW"); commands.add("2"); commands.add("3"); commands.add("3"); commands.add("4");
        assertThrows(DiceNotFoundException.class, ()->playerTurns.get(0).getMoves().get(1).performMove(commands));
    }
}
