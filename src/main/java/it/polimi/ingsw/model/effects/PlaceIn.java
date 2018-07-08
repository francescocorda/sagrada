package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.model.game.WindowFrame;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.model.game.Game.INVALID_MOVE_BY_PLAYER;
import static it.polimi.ingsw.model.effects.Element.DRAFTPOOL;
import static it.polimi.ingsw.model.effects.Element.WINDOW;
import static it.polimi.ingsw.model.effects.RemoveFrom.INVALID_COORDINATES;


public class PlaceIn extends Effect {

    private Element element;
    private static final HashMap<Element, String> descriptions = descriptions();
    private static final HashMap<Element, Boolean> stops = stops();
    private static final HashMap<Element, Integer> lenghts = lengths();

    /**
     * @return the description associated with the {@link Element} element of the effect
     */
    static HashMap<Element, String> descriptions() {
        HashMap<Element, String> temp = new HashMap<>();
        temp.put(DRAFTPOOL, "The dice will be placed in the draft pool");
        temp.put(WINDOW, "Choose the window cell in which to place the dice.");
        return temp;
    }

    /**
     * @return the boolean associated with the {@link Element} element of the effect
     */
    static HashMap<Element, Boolean> stops() {
        HashMap<Element, Boolean> temp = new HashMap<>();
        temp.put(DRAFTPOOL, false);
        temp.put(WINDOW, true);
        return temp;
    }

    /**
     * @return the command's length associated with the {@link Element} element of the effect
     */
    static HashMap<Element, Integer> lengths() {
        HashMap<Element, Integer> temp = new HashMap<>();
        temp.put(DRAFTPOOL, 0);
        temp.put(WINDOW, 2);
        return temp;
    }

    /**
     * create a new {@link PlaceIn} given the parameter
     * @param element is the given parameter that defines the effect's behaviour
     */
    public PlaceIn(Element element) {
        this.element = element;
        this.stop = stops.get(element);
        this.commandsLength = lenghts.get(element);
    }

    /**
     * Place the active dice in an element of the table accordingly to the {@link Element}
     * attribute of the class:
     * element = DRAWPOOL --> place the dice in the draw pool
     * element = WINDOW --> place the dice in the player's window
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) {
        boolean result = false;
        if (element == DRAFTPOOL) {
            int indexDP = -100;
            if(!commands.isEmpty()) {
                indexDP = Integer.parseInt(commands.remove(0));
            } else if (round.getPlayerTurn(0).getOriginCoordinates().size()>=1) {
                int size = round.getPlayerTurn(0).getOriginCoordinates().size()-1;
                indexDP = round.getPlayerTurn(0).removeOriginCoordinate(size);
            }
            try {
                table.getDrawPool().add(indexDP - 1, table.getActiveDice());
                table.setActiveDice(null);
                table.notifyObservers();
                result = true;
            } catch (IndexOutOfBoundsException e) {
                table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" +
                        INVALID_COORDINATES);
            }
        } else if (element == Element.WINDOW && canAcceptAPlacement(table, round)) {
            WindowFrame window = round.getPlayerTurn(0).getPlayer().getWindowFrame();
            if (!commands.isEmpty()) {
                int row = Integer.parseInt(commands.remove(0));
                int col = Integer.parseInt(commands.remove(0));
                try {
                    window.setDice(row, col, table.getActiveDice());
                    if (table.getDrawPool().size() < round.getPlayerTurn(0).getDraftPoolSize()) {
                        round.getPlayerTurn(0).setDraftPoolSize(table.getDrawPool().size());
                        round.getPlayerTurn(0).setMovesLeft(round.getPlayerTurn(0).getMovesLeft() - 1);
                    }
                    window.getPatternCard().disableExceptions();
                    table.setActiveDice(null);
                    table.notifyObservers();
                    result = true;
                } catch (MismatchedRestrictionException | InvalidNeighboursException | InvalidFirstMoveException | OccupiedCellException e) {
                    table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" + e.getMessage());
                } catch (IndexOutOfBoundsException e) {
                    table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" +
                            INVALID_COORDINATES);
                }
            }
        }
        return result;
    }

    /**
     * explains the effect accordingly to the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(element));
    }

    /**
     * @return the element of the table activated by the effect
     */
    @Override
    public String getActiveTableElement() {
        return element.toString() + "_IN";
    }

    /**
     * @param table is the given {@link Table}
     * @param round is the given {@link Round}
     * @return true if the Window has at least one cell in which a dice can be placed
     */
    public boolean canAcceptAPlacement(Table table, Round round) {
        WindowFrame windowFrame = round.getCurrentPlayer().getWindowFrame();
        for (int i = 0; i < PatternCard.ROW; i++) {
            for (int j = 0; j < PatternCard.COLUMN; j++) {
                try {
                    windowFrame.setDice(i+1, j+1, table.getActiveDice());
                    windowFrame.removeDice(i+1, j+1);
                    return true;
                } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | DiceNotFoundException e) {
                    //
                }

            }
        }
        table.getDrawPool().add(table.getActiveDice());
        table.setActiveDice(null);
        return false;
    }
}
