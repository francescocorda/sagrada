package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.model.game.WindowFrame;
import it.polimi.ingsw.exceptions.DiceNotFoundException;
import it.polimi.ingsw.exceptions.ImpossibleMoveException;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.model.game.Game.INVALID_MOVE_BY_PLAYER;
import static it.polimi.ingsw.model.effects.Element.DRAFTPOOL;
import static it.polimi.ingsw.model.effects.Element.WINDOW;


public class RemoveFrom extends Effect {

    private static final HashMap<Element, String> descriptions = descriptions();
    private static final HashMap<Element, Integer> lenghts = lenghts();
    public static final String INVALID_COORDINATES = "Invalid coordinates.";

    static HashMap<Element, String> descriptions() {
        HashMap<Element, String> temp = new HashMap<>();
        temp.put(DRAFTPOOL, "Choose the dice to remove from the draft pool.");
        temp.put(WINDOW, "Choose the dice to remove from the window.");
        return temp;
    }

    static HashMap<Element, Integer> lenghts() {
        HashMap<Element, Integer> temp = new HashMap<>();
        temp.put(DRAFTPOOL, 1);
        temp.put(WINDOW, 2);
        return temp;
    }


    private Element element;

    public RemoveFrom(Element element) {
        this.element = element;
        this.stop = true;
        this.commandsLenght = lenghts.get(element);
    }

    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) throws ImpossibleMoveException {
        if (element == DRAFTPOOL) {
            int indexDP = Integer.parseInt(commands.remove(0));
            round.getPlayerTurn(0).setDraftPoolSize(table.getDraftPool().size());
            round.getPlayerTurn(0).addOriginCoordinate(indexDP);
            try {
                table.setActiveDice(table.getDraftPool().get(indexDP - 1));
                table.getDraftPool().remove(indexDP-1);
                table.notifyObservers();
                return true;
            } catch (IndexOutOfBoundsException e) {
                round.getPlayerTurn(0).removeOriginCoordinate();
                table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" +
                        INVALID_COORDINATES);
            }
        } else if (element == WINDOW) {
            WindowFrame window = round.getPlayerTurn(0).getPlayer().getWindowFrame();
            if (!window.isEmpty()) {
                int row = Integer.parseInt(commands.remove(0));
                int col = Integer.parseInt(commands.remove(0));
                round.getPlayerTurn(0).addOriginCoordinate(row);
                round.getPlayerTurn(0).addOriginCoordinate(col);
                try {
                    table.setActiveDice(round.getPlayerTurn(0).getPlayer().getWindowFrame().removeDice(row, col));
                    table.notifyObservers();
                    return true;
                } catch (IndexOutOfBoundsException | DiceNotFoundException e) {
                    round.getPlayerTurn(0).removeOriginCoordinate();
                    round.getPlayerTurn(0).removeOriginCoordinate();
                    table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" +
                            INVALID_COORDINATES);
                }
            } else {
                throw new ImpossibleMoveException();
            }
            }
        return false;
    }


    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(element));
    }

    @Override
    public String getActiveTableElement() {
        return element.toString() + "_OUT";
    }
}
