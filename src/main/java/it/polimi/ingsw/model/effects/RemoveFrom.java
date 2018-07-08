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
    private static final HashMap<Element, Integer> lenghts = lengths();
    public static final String INVALID_COORDINATES = "Invalid coordinates.";

    /**
     * @return the description associated with the {@link Element} element of the effect
     */
    static HashMap<Element, String> descriptions() {
        HashMap<Element, String> temp = new HashMap<>();
        temp.put(DRAFTPOOL, "Choose the dice to remove from the draft pool.");
        temp.put(WINDOW, "Choose the dice to remove from the window.");
        return temp;
    }

    /**
     * @return the boolean associated with the {@link Element} element of the effect
     */
    static HashMap<Element, Integer> lengths() {
        HashMap<Element, Integer> temp = new HashMap<>();
        temp.put(DRAFTPOOL, 1);
        temp.put(WINDOW, 2);
        return temp;
    }


    private Element element;

    /**
     * create a new {@link RemoveFrom} given the parameter
     * @param element is the given parameter that defines the effect's behaviour
     */
    public RemoveFrom(Element element) {
        this.element = element;
        this.stop = true;
        this.commandsLength = lenghts.get(element);
    }

    /**
     * Remove a dice in an element of the table accordingly to the {@link Element} attribute of the class:
     * element = DRAWPOOL --> place the dice in the draw pool
     * element = WINDOW --> place the dice in the player's window
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     * @throws ImpossibleMoveException if the combination of commands given by the controller or the current state
     *      * of the game leads to a state that do not allow any proceeding
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) throws ImpossibleMoveException {
        if (element == DRAFTPOOL) {
            int indexDP = Integer.parseInt(commands.remove(0));
            round.getPlayerTurn(0).setDraftPoolSize(table.getDrawPool().size());
            round.getPlayerTurn(0).addOriginCoordinate(indexDP);
            try {
                table.setActiveDice(table.getDrawPool().get(indexDP - 1));
                table.getDrawPool().remove(indexDP-1);
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
        return element.toString() + "_OUT";
    }
}
