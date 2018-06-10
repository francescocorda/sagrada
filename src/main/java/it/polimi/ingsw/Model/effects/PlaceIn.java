package it.polimi.ingsw.Model.effects;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Game.Round;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.Model.Game.WindowFrame;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.Model.Game.Game.INVALID_MOVE_BY_PLAYER;
import static it.polimi.ingsw.Model.effects.Element.DRAFTPOOL;
import static it.polimi.ingsw.Model.effects.Element.WINDOW;
import static it.polimi.ingsw.Model.effects.RemoveFrom.INVALID_COORDINATES;


public class PlaceIn extends Effect {

    private Element element;
    private static final HashMap<Element, String> descriptions = descriptions();
    private static final HashMap<Element, Boolean> stops = stops();
    private static final HashMap<Element, Integer> lenghts = lenghts();

    static HashMap<Element, String> descriptions() {
        HashMap<Element, String> temp = new HashMap<>();
        temp.put(DRAFTPOOL, "The dice will be placed in the draft pool");
        temp.put(WINDOW, "Choose the window cell in which to place the dice.");
        return temp;
    }

    static HashMap<Element, Boolean> stops() {
        HashMap<Element, Boolean> temp = new HashMap<>();
        temp.put(DRAFTPOOL, false);
        temp.put(WINDOW, true);
        return temp;
    }

    static HashMap<Element, Integer> lenghts() {
        HashMap<Element, Integer> temp = new HashMap<>();
        temp.put(DRAFTPOOL, 0);
        temp.put(WINDOW, 2);
        return temp;
    }

    public PlaceIn(Element element) {
        this.element = element;
        this.stop = stops.get(element);
        this.commandsLenght = lenghts.get(element);
    }

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
                table.getDraftPool().add(indexDP - 1, table.getActiveDice());
                table.setActiveDice(null);
                table.notifyObservers();
                result = true;
            } catch (IndexOutOfBoundsException e) {
                table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" +
                        INVALID_COORDINATES);
            }
        } else if (element == Element.WINDOW && isPlaceable(table, round)) {
            WindowFrame window = round.getPlayerTurn(0).getPlayer().getWindowFrame();
            if (!commands.isEmpty()) {
                int row = Integer.parseInt(commands.remove(0));
                int col = Integer.parseInt(commands.remove(0));
                try {
                    window.setDice(row, col, table.getActiveDice());
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

    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(element));
    }

    public boolean isPlaceable(Table table, Round round) {
        //String activeException;

        WindowFrame windowFrame = round.getCurrentPlayer().getWindowFrame();
        //activeException = windowFrame.getActiveException();
        for (int i = 0; i < PatternCard.ROW; i++) {
            for (int j = 0; j < PatternCard.COLUMN; j++) {
                try {
                    windowFrame.setDice(i+1, j+1, table.getActiveDice());
                    windowFrame.removeDice(i+1, j+1);
                    //windowFrame.enableException(activeException);
                    return true;
                } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException | DiceNotFoundException e) {
                    //windowFrame.enableException(activeException);
                }

            }
        }
        table.getDraftPool().add(table.getActiveDice());
        table.setActiveDice(null);
        return false;
    }
}
