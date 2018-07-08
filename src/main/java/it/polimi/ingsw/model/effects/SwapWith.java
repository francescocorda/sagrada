package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.Dice;
import it.polimi.ingsw.model.game.Round;
import it.polimi.ingsw.model.game.Table;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.model.game.Game.INVALID_MOVE_BY_PLAYER;
import static it.polimi.ingsw.model.effects.Element.DICEBAG;
import static it.polimi.ingsw.model.effects.Element.ROUNDTRACK;
import static it.polimi.ingsw.model.effects.RemoveFrom.INVALID_COORDINATES;


public class SwapWith extends Effect{

    private Element element;

    private static final HashMap<Element, String> descriptions = descriptions();
    private static final HashMap<Element, Boolean> stops = stops();
    private static final HashMap<Element, Integer> lengths = lengths();

    /**
     * @return the description associated with the {@link Element} element of the effect
     */
    static HashMap<Element, String> descriptions() {
        HashMap<Element, String> temp = new HashMap<>();
        temp.put(ROUNDTRACK, "Choose the dice in the round track to swap.");
        temp.put(DICEBAG, "The dice will be swapped with one in the dice bag.");
        return temp;
    }

    /**
     * @return the boolean associated with the {@link Element} element of the effect
     */
    static HashMap<Element, Boolean> stops() {
        HashMap<Element, Boolean> temp = new HashMap<>();
        temp.put(ROUNDTRACK, true);
        temp.put(DICEBAG, false);
        return temp;
    }

    /**
     * @return the command's length associated with the {@link Element} element of the effect
     */
    static HashMap<Element, Integer> lengths() {
        HashMap<Element, Integer> temp = new HashMap<>();
        temp.put(ROUNDTRACK, 2);
        temp.put(DICEBAG, 0);
        return temp;
    }

    /**
     * creates a new {@link SwapWith} given the parameter
     * @param element is the given parameter that defines the effect's behaviour
     */
    public SwapWith(Element element) {
        this.element = element;
        this.stop = stops.get(element);
        this.commandsLength = lengths.get(element);
    }

    /**
     * Swaps the active dice with one contained in an element of the table, accordingly to the {@link Element}
     * attribute of the class:
     * element = ROUNDTRACK --> swaps the active dice with one of the round track
     * element = DICEBAG --> swaps the active dice with one of the dice bag
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) {
        if (element == ROUNDTRACK) {
            int indexRound = Integer.parseInt(commands.remove(0));
            int indexDice = Integer.parseInt(commands.remove(0));
            try {
                Dice dice = table.getRoundTrack().getRoundDices(indexRound-1).get(indexDice-1);
                table.getRoundTrack().getRoundDices(indexRound-1).add(indexDice-1, table.getActiveDice());
                table.getRoundTrack().getRoundDices(indexRound-1).remove(dice);
                table.setActiveDice(dice);
                table.notifyObservers();
                return true;
            } catch (IndexOutOfBoundsException e) {
                table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() + ":\n" +
                        INVALID_COORDINATES);
            }
        } else if (element == Element.DICEBAG) {
            table.getDiceBag().addDice(table.getActiveDice());
            table.setActiveDice(null);
            table.setActiveDice(table.getDiceBag().draw());
            table.notifyObservers();
            return true;
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
     *
     * @return the element of the table activated by the effect
     */
    @Override
    public String getActiveTableElement() {
        return element.toString();
    }

}
