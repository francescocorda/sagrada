package it.polimi.ingsw.model.cards.toolcard;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.cards.patterns.PatternDeck;
import it.polimi.ingsw.database.ParserManager;
import it.polimi.ingsw.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestToolCards {
    ArrayList<ToolCard> toolCards;
    Table table;
    PatternDeck patternDeck;
    ArrayList<Player> players;
    Game game;
    private ParserManager pm = ParserManager.getParserManager();

    @BeforeEach
    public void test() {

        toolCards = pm.getToolCards();
        patternDeck = new PatternDeck(pm.getPatternDeck());
        ArrayList<String> names = new ArrayList<>();
        names.add("luca");
        names.add("checco");
        names.add("fede");
        names.add("ale");
        game = new Game(1, names);
        game.getTable().setDraftPool(9);
        table = game.getTable();
        players = game.getTable().getPlayers();
        for (Player player: players) {
            try {
                player.setPatternCard(patternDeck.getPatternCard(0));
            } catch (NotValidInputException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void toolCard1Test() throws WrongRoundException, InvalidNeighboursException, ImpossibleMoveException {
        for (Dice c : game.getTable().getDraftPool()) {
            if (c.valueOf() == 1 || c.valueOf() == 6) {
                try {
                    c.setFace(3);
                } catch (InvalidFaceException e) {
                    e.printStackTrace();
                }
            }
        }

        ToolCard toolCard = toolCards.get(0);
        ArrayList<String> commands = new ArrayList<>();
        int dimension = game.getTable().getDraftPool().size();
        Dice dice1 = game.getTable().getDraftPool().get(0);
        int value = dice1.valueOf();



        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("+1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));


        //funzionamento base
        assertEquals(dimension, game.getTable().getDraftPool().size());
        assertEquals(value, game.getTable().getDraftPool().get(0).valueOf() - 1);

        //verifica che non posso trasformare un 1 in 6
        try {
            game.getTable().getDraftPool().get(0).setFace(1);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dimension = game.getTable().getDraftPool().size();

        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("-1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        assertEquals(dimension-1, game.getTable().getDraftPool().size());
        assertEquals(1, game.getTable().getActiveDice().valueOf());

        ArrayList<String> commands1 = new ArrayList<>();
        commands1.add("+1");
        toolCard.useToolCard(commands1, game.getTable(), game.getRounds().get(0));
        assertEquals(dimension, game.getTable().getDraftPool().size());
        assertEquals(2, game.getTable().getDraftPool().get(0).valueOf());

        game.getTable().dumpDraftPool();

        //verifica che non posso trasformare un 6 in 1;
        try {
            game.getTable().getDraftPool().get(0).setFace(6);

        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        game.getTable().dumpDraftPool();
        dimension = game.getTable().getDraftPool().size();
        ArrayList<String> commands2 = new ArrayList<>();
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands2.add("1");
        toolCard.useToolCard( commands2, game.getTable(), game.getRounds().get(0));
        table.dumpDraftPool();
        commands2.add("+1");
        toolCard.useToolCard( commands2, game.getTable(), game.getRounds().get(0));
        game.getTable().dumpDraftPool();

        assertEquals(dimension-1, game.getTable().getDraftPool().size());
        assertEquals(6, game.getTable().getActiveDice().valueOf());
        ArrayList<String> commands3 = new ArrayList<>();
        commands3.add("-1");
        toolCard.useToolCard( commands3, game.getTable(), game.getRounds().get(0));
        assertEquals(dimension, game.getTable().getDraftPool().size());
        assertEquals(5, game.getTable().getDraftPool().get(0).valueOf());
    }

    @Test
    public void toolCard2Test() throws WrongRoundException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(1);

        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        ArrayList<String> commands = new ArrayList<>();

        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        windowFrame.dump();



        int dimension = table.getDraftPool().size();

        //funzionamento base
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm1 = new ArrayList<>();
        comm1.add("2"); comm1.add("2");
        toolCard.useToolCard(comm1, game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm2 = new ArrayList<>();
        comm2.add("1"); comm2.add("2");
        toolCard.useToolCard(comm2, game.getTable(), game.getRounds().get(0));
        windowFrame.dump();

        assertEquals(dimension, game.getTable().getDraftPool().size());
        assertEquals(Color.GREEN, windowFrame.getDice(1, 2).getColor());
        assertEquals(3, windowFrame.getDice(1, 2).valueOf());


        //verifica che devo rispettare restrizione numero
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm3 = new ArrayList<>();
        comm3.add("1"); comm3.add("2");
        toolCard.useToolCard(comm3, game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm4 = new ArrayList<>();
        comm4.add("2"); comm4.add("3");
        toolCard.useToolCard(comm4, game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm5 = new ArrayList<>();
        comm5.add("1"); comm5.add("2");
        toolCard.useToolCard(comm5, game.getTable(), game.getRounds().get(0));

        WindowFrame window = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        assertEquals(dimension,  game.getTable().getDraftPool().size());
        assertEquals(Color.GREEN,  window.getDice(1,2).getColor());
        assertEquals(3,  window.getDice(1,2).valueOf());
        assertEquals(false, windowFrame.getPatternCard().getExceptionRestriction(1,2));
        assertEquals(false, windowFrame.getPatternCard().getExceptionPosition(1,2));

        game.getRounds().get(0).getCurrentPlayer().getWindowFrame().dump();

        //verifica che devo rispettare restrizione posizione
        toolCard.useToolCard(new ArrayList<>(),  game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("1"); commands.add("2");
        toolCard.useToolCard(commands,  game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("4"); commands.add("2");
        toolCard.useToolCard(commands,  game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm6 = new ArrayList<>();
        comm6.add("1"); comm6.add("2");
        toolCard.useToolCard(comm6, game.getTable(), game.getRounds().get(0));

        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();
        windowFrame.dump();

        assertEquals(dimension,  game.getTable().getDraftPool().size());
        assertEquals(Color.GREEN,  window.getDice(1,2).getColor());
        assertEquals(3,  window.getDice(1,2).valueOf());
        assertEquals(false, windowFrame.getPatternCard().getExceptionRestriction(1,2));
        assertEquals(false, windowFrame.getPatternCard().getExceptionPosition(1,2));
    }


    @Test
    public void toolCard3Test() throws WrongRoundException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(2);

        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        ArrayList<String> commands = new ArrayList<>();
        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        windowFrame.dump();


        int dimension = table.getDraftPool().size();

        //funzionamento base
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm1 = new ArrayList<>();
        comm1.add("2"); comm1.add("2");
        toolCard.useToolCard(comm1, game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm2 = new ArrayList<>();
        comm2.add("2"); comm2.add("3");
        toolCard.useToolCard(comm2, game.getTable(), game.getRounds().get(0));
        windowFrame.dump();

        assertEquals(dimension, game.getTable().getDraftPool().size());
        assertEquals(Color.GREEN, windowFrame.getDice(2, 3).getColor());
        assertEquals(3, windowFrame.getDice(2, 3).valueOf());


        //verifica che devo rispettare restrizione colore
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm3 = new ArrayList<>();
        comm3.add("2"); comm3.add("3");
        toolCard.useToolCard(comm3, game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm4 = new ArrayList<>();
        comm4.add("1"); comm4.add("2");
        toolCard.useToolCard(comm4, game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm5 = new ArrayList<>();
        comm5.add("2"); comm5.add("3");
        toolCard.useToolCard(comm5, game.getTable(), game.getRounds().get(0));

        WindowFrame window = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        assertEquals(dimension,  game.getTable().getDraftPool().size());
        assertEquals(Color.GREEN,  window.getDice(2,3).getColor());
        assertEquals(3,  window.getDice(2,3).valueOf());
        assertEquals(false, windowFrame.getPatternCard().getExceptionRestriction(2,3));
        assertEquals(false, windowFrame.getPatternCard().getExceptionPosition(2,3));

        game.getRounds().get(0).getCurrentPlayer().getWindowFrame().dump();

        //verifica che devo rispettare restrizione posizione
        toolCard.useToolCard(new ArrayList<>(),  game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("2"); commands.add("3");
        toolCard.useToolCard(commands,  game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("4"); commands.add("2");
        toolCard.useToolCard(commands,  game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm6 = new ArrayList<>();
        comm6.add("2"); comm6.add("3");
        toolCard.useToolCard(comm6, game.getTable(), game.getRounds().get(0));

        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();
        windowFrame.dump();

        assertEquals(dimension,  game.getTable().getDraftPool().size());
        assertEquals(Color.GREEN,  window.getDice(2,3).getColor());
        assertEquals(3,  window.getDice(2,3).valueOf());
        assertEquals(false, windowFrame.getPatternCard().getExceptionRestriction(2,3));
        assertEquals(false, windowFrame.getPatternCard().getExceptionPosition(2,3));
    }

    @Test
    public void toolCard41Test() throws WrongRoundException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(3);
        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        ArrayList<String> commands = new ArrayList<>();
        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        windowFrame.dump();


        int dimension = table.getDraftPool().size();

        //funzionamento base
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm1 = new ArrayList<>();
        comm1.add("1"); comm1.add("3");
        toolCard.useToolCard(comm1, game.getTable(), game.getRounds().get(0));
        ArrayList<String> comm2 = new ArrayList<>();
        comm2.add("3"); comm2.add("2");
        toolCard.useToolCard(comm2, game.getTable(), game.getRounds().get(0));
        windowFrame.dump();
        ArrayList<String> comm3 = new ArrayList<>();
        comm3.add("2"); comm3.add("2");
        toolCard.useToolCard(comm3, game.getTable(), game.getRounds().get(0));
        windowFrame.dump();
        ArrayList<String> comm4 = new ArrayList<>();
        comm4.add("4"); comm4.add("2");
        toolCard.useToolCard(comm4, game.getTable(), game.getRounds().get(0));
        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();
        windowFrame.dump();


        assertEquals(dimension, table.getDraftPool().size());
        assertEquals(Color.BLUE, windowFrame.getDice(3, 2).getColor());
        assertEquals(2, windowFrame.getDice(3, 2).valueOf());
        assertEquals(Color.GREEN, windowFrame.getDice(4, 2).getColor());
        assertEquals(3, windowFrame.getDice(4, 2).valueOf());

    }

    @Test
    public void toolCard5Test() throws ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(4);

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        int dimensionDP = table.getDraftPool().size();
        Color diceColorDP = table.getDraftPool().get(0).getColor();
        int diceFaceDP = table.getDraftPool().get(0).valueOf();
        table.dumpDraftPool();

        table.getRoundTrack().setRoundDices(table.getDiceBag().draw(9),0);
        table.getRoundTrack().setRoundDices(table.getDiceBag().draw(8),1);
        table.getRoundTrack().dump();

        int dimensionRT1 = table.getRoundTrack().getRoundDices(0).size();
        Color diceColorRT = table.getRoundTrack().getRoundDices(0).get(0).getColor();
        int diceFaceRT = table.getRoundTrack().getRoundDices(0).get(0).valueOf();

        table.getRoundTrack().dump();

        ArrayList<String> commands = new ArrayList<>();
        //funzionamento base
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("1"); commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        table.dumpDraftPool();
        table.getRoundTrack().dump();

        assertEquals(dimensionDP, table.getDraftPool().size());
        assertEquals(dimensionRT1, table.getRoundTrack().getRoundDices(0).size());
        assertEquals(diceFaceDP, table.getRoundTrack().getRoundDices(0).get(0).valueOf());
        assertEquals(diceColorDP, table.getRoundTrack().getRoundDices(0).get(0).getColor());
        assertEquals(diceFaceRT, table.getDraftPool().get(0).valueOf());
        assertEquals(diceColorRT, table.getDraftPool().get(0).getColor());

    }

    @Test
    public void toolCard6Test() throws WrongRoundException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(5);

        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        ArrayList<String> commands = new ArrayList<>();
        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        windowFrame.dump();


        int dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.RED);

        table.dumpDraftPool();
        //funzionamento base
        commands = new ArrayList<>();
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("3"); commands.add("3");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        windowFrame.dump();
        table.dumpDraftPool();
        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(Color.RED, windowFrame.getDice(3, 3).getColor());


        //verifica che devo rispettare restrizione colore
        dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.RED);
        table.dumpDraftPool();



        commands = new ArrayList<>();
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("1"); commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        Dice dice = table.getActiveDice();
        commands = new ArrayList<>();
        commands.add("2"); commands.add("4");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();
        windowFrame.dump();

        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(null, windowFrame.getDice(1, 1));
        assertEquals(dice, windowFrame.getDice(2, 4));
        table.dumpDraftPool();
        windowFrame.dump();

    }

    @Test
    public void toolCard7Test() throws ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(6);

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        int dimensionDP = table.getDraftPool().size();
        table.dumpDraftPool();

        ArrayList<String> commands = new ArrayList<>();

        //funzionamento base
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        table.dumpDraftPool();
        table.getRoundTrack().dump();

        assertEquals(dimensionDP, table.getDraftPool().size());

    }

    @Test
    public void toolCard8Test() throws WrongRoundException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(7);

        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        ArrayList<String> commands = new ArrayList<>();
        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        windowFrame.dump();


        int dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.PURPLE);
        try {
            table.getDraftPool().get(0).setFace(1);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        //funzionamento base
        commands = new ArrayList<>();
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("3"); commands.add("2");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));


        windowFrame.dump();
        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(Color.PURPLE, windowFrame.getDice(3, 2).getColor());
        assertEquals(1, windowFrame.getDice(3, 2).valueOf());


        //verifica che devo rispettare restrizione colore
        dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.YELLOW);
        try {
            table.getDraftPool().get(0).setFace(4);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dimension = table.getDraftPool().size();
        commands = new ArrayList<>();
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("3"); commands.add("3");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("4"); commands.add("3");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();
        windowFrame.dump();

        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(Color.YELLOW, windowFrame.getDice(4, 3).getColor());
        assertEquals(4, windowFrame.getDice(4, 3).valueOf());
        assertEquals(null, windowFrame.getDice(3, 3));


        //verifica che devo rispettare restrizione posizione
        commands.clear();
        table.getDraftPool().get(0).setColor(Color.PURPLE);
        try {
            table.getDraftPool().get(0).setFace(1);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        dimension = table.getDraftPool().size();
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("1"); commands.add("5");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("3"); commands.add("4");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(Color.PURPLE, windowFrame.getDice(3, 4).getColor());
        assertEquals(1, windowFrame.getDice(3, 4).valueOf());
        assertEquals(null, windowFrame.getDice(1, 5));
    }


    @Test
    public void toolCard9Test() throws WrongRoundException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(8);

        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        ArrayList<String> commands = new ArrayList<>();
        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        windowFrame.dump();


        int dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.PURPLE);
        try {
            table.getDraftPool().get(0).setFace(1);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        //funzionamento base
        commands = new ArrayList<>();
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("4"); commands.add("3");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        windowFrame.dump();

        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(Color.PURPLE, windowFrame.getDice(4, 3).getColor());
        assertEquals(1, windowFrame.getDice(4, 3).valueOf());


        //verifica che devo rispettare restrizione colore
        dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.YELLOW);
        try {
            table.getDraftPool().get(0).setFace(4);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        commands = new ArrayList<>();
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("3"); commands.add("5");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("4"); commands.add("5");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();
        windowFrame.dump();

        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(Color.YELLOW, windowFrame.getDice(4, 5).getColor());
        assertEquals(4, windowFrame.getDice(4, 5).valueOf());
        assertEquals(null, windowFrame.getDice(3, 5));


        //verifica che devo rispettare restrizione faccia
        dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.PURPLE);
        try {
            table.getDraftPool().get(0).setFace(2);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        commands = new ArrayList<>();
        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("1"); commands.add("5");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands.add("4"); commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));


        windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        assertEquals(dimension-1, table.getDraftPool().size());
        assertEquals(Color.PURPLE, windowFrame.getDice(4, 1).getColor());
        assertEquals(2, windowFrame.getDice(4, 1).valueOf());
        assertEquals(null, windowFrame.getDice(1, 5));
    }

    @Test
    public void toolCard10Test() throws WrongRoundException, InvalidNeighboursException, ImpossibleMoveException {
        for (Dice c : game.getTable().getDraftPool()) {
            if (c.valueOf() == 1 || c.valueOf() == 6) {
                try {
                    c.setFace(3);
                } catch (InvalidFaceException e) {
                    e.printStackTrace();
                }
            }
        }

        ToolCard toolCard = toolCards.get(9);
        ArrayList<String> commands = new ArrayList<>();
        int dimension = game.getTable().getDraftPool().size();
        Dice dice1 = game.getTable().getDraftPool().get(0);
        int value = dice1.valueOf();



        toolCard.useToolCard(new ArrayList<>(), game.getTable(), game.getRounds().get(0));
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));



        //funzionamento base
        assertEquals(dimension, game.getTable().getDraftPool().size());
        assertEquals(value, 7-table.getDraftPool().get(0).valueOf());

    }

    @Test
    public void toolCard11Test() throws WrongRoundException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(10);

        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }

        ArrayList<String> commands = new ArrayList<>();
        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) {
            e.printStackTrace();
        }
        windowFrame.dump();

        table.dumpDraftPool();
        int dimension = table.getDraftPool().size();
        table.getDraftPool().get(0).setColor(Color.PURPLE);
        try {
            table.getDraftPool().get(0).setFace(1);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        //funzionamento base
        commands = new ArrayList<>();
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("1");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("5");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("2"); commands.add("4");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        windowFrame.dump();
        table.dumpDraftPool();

        assertEquals(dimension-1, table.getDraftPool().size());
        assertNotEquals(null, windowFrame.getDice(2, 4));

    }


    @Test
    public void toolCard12Test() throws WrongRoundException, InvalidFirstMoveException, ImpossibleMoveException {

        ToolCard toolCard = toolCards.get(11);


        WindowFrame windowFrame = game.getRounds().get(0).getCurrentPlayer().getWindowFrame();

        for(int i=0; i<10; i++){
            ArrayList<Dice> dices = new ArrayList<>();
            for(int j=0; j<2; j++){
                Dice dice = new Dice(Color.BLUE);
                dice.roll();
                dices.add(dice);

            }
            table.getRoundTrack().setRoundDices(dices, i);
        }



        table.getDraftPool().get(0).setColor(Color.BLUE);
        table.getDraftPool().get(1).setColor(Color.BLUE);
        table.getDraftPool().get(2).setColor(Color.GREEN);
        try {
            table.getDraftPool().get(0).setFace(2);
            table.getDraftPool().get(1).setFace(5);
            table.getDraftPool().get(2).setFace(3);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
        table.dumpDraftPool();

        ArrayList<String> commands = new ArrayList<>();

        try {
            windowFrame.setDice(1,3,table.getDraftPool().remove(0));
            windowFrame.setDice(2,2,table.getDraftPool().remove(0));
            windowFrame.setDice(3,2,table.getDraftPool().remove(0));
        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException e) {
            e.printStackTrace();
        }
        windowFrame.dump();
        table.getRoundTrack().dump();

        //funzionamento base
        commands = new ArrayList<>();
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("1"); commands.add("3");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("4");commands.add("2");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("2"); commands.add("2");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));
        commands = new ArrayList<>();
        commands.add("2");commands.add("3");
        toolCard.useToolCard(commands, game.getTable(), game.getRounds().get(0));

        windowFrame.dump();
        table.getRoundTrack().dump();

        assertEquals(Color.BLUE, windowFrame.getDice(4,2).getColor());
        assertEquals(2, windowFrame.getDice(4,2).valueOf());
        assertEquals(Color.BLUE, windowFrame.getDice(2,3).getColor());
        assertEquals(5, windowFrame.getDice(2,3).valueOf());

    }
}
