package it.polimi.ingsw.Model.effects;

import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.Round;
import it.polimi.ingsw.Model.Game.Table;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.Model.Game.Game.INVALID_MOVE_BY_PLAYER;
import static it.polimi.ingsw.Model.effects.Element.DICEBAG;
import static it.polimi.ingsw.Model.effects.Element.ROUNDTRACK;
import static it.polimi.ingsw.Model.effects.RemoveFrom.INVALID_COORDINATES;


public class SwapWith extends Effect{

    private Element element;

    private static final HashMap<Element, String> descriptions = createMap();
    private static final HashMap<Element, Boolean> stops = stops();
    private static final HashMap<Element, Integer> lenghts = lenghts();

    static HashMap<Element, String> createMap() {
        HashMap<Element, String> temp = new HashMap<>();
        temp.put(ROUNDTRACK, "Choose the dice in the round track to swap.");
        temp.put(DICEBAG, "The dice will be swapped with one in the dice bag.");
        return descriptions;
    }
    static HashMap<Element, Boolean> stops() {
        HashMap<Element, Boolean> temp = new HashMap<>();
        temp.put(ROUNDTRACK, true);
        temp.put(DICEBAG, false);
        return temp;
    }

    static HashMap<Element, Integer> lenghts() {
        HashMap<Element, Integer> temp = new HashMap<>();
        temp.put(ROUNDTRACK, 2);
        temp.put(DICEBAG, 0);
        return temp;
    }

    public SwapWith(Element element) {
        this.element = element;
        this.stop = stops.get(element);
        this.commandsLenght = lenghts.get(element);
    }

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
                explainEffect(table, round);
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

    @Override
    public void explainEffect(Table table, Round round) {
        //table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + descriptions.get(element));
    }

}
