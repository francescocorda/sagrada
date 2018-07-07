package it.polimi.ingsw.model.cards.private_objectives;

import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.Serializable;
import java.util.ArrayList;

public class PrivateObjectiveDeck implements Serializable {
    private ArrayList<PrivateObjectiveCard> deck;

    private static final String RED_SHADES = "Red shades";
    private static final String GREEN_SHADES = "Green shades";
    private static final String YELLOW_SHADES = "Yellow shades";
    private static final String BLU_SHADES = "Blue shades";
    private static final String PURPLE_SHADES = "Purple shades";
    private static final String COLOR_RED = "red";
    private static final String COLOR_GREEN = "green";
    private static final String COLOR_YELLOW = "yellow";
    private static final String COLOR_BLUE = "blue";
    private static final String COLOR_PURPLE = "purple";
    private static final String SUM_VALUES_OF_ALL = "Sum values of all";
    private static final String DICES = " dices";


    /**
     * creates a new {@link PrivateObjectiveDeck}
     */
    public PrivateObjectiveDeck() {
        deck = new ArrayList<>();

        PrivateObjectiveCard red;
        PrivateObjectiveCard green;
        PrivateObjectiveCard yellow;
        PrivateObjectiveCard blue;
        PrivateObjectiveCard purple;

        red = new PrivateObjectiveCard(RED_SHADES, 1, Color.RED);
        red.setDescription(SUM_VALUES_OF_ALL + COLOR_RED + DICES);
        green = new PrivateObjectiveCard(GREEN_SHADES, 2, Color.GREEN);
        green.setDescription(SUM_VALUES_OF_ALL + COLOR_GREEN + DICES);
        yellow = new PrivateObjectiveCard(YELLOW_SHADES, 3, Color.YELLOW);
        yellow.setDescription(SUM_VALUES_OF_ALL + COLOR_YELLOW + DICES);
        blue = new PrivateObjectiveCard(BLU_SHADES, 4, Color.BLUE);
        blue.setDescription(SUM_VALUES_OF_ALL + COLOR_BLUE + DICES);
        purple = new PrivateObjectiveCard(PURPLE_SHADES, 5, Color.PURPLE);
        purple.setDescription(SUM_VALUES_OF_ALL + COLOR_PURPLE + DICES);

        deck.add(red);
        deck.add(green);
        deck.add(yellow);
        deck.add(blue);
        deck.add(purple);


    }

    /**
     * @return {@link #deck}.
     */
    public ArrayList<PrivateObjectiveCard> getPrivateObjectiveDeck() {
        return this.deck;
    }

    /**
     * @param index : the given {@link Integer} index
     * @return the {@link PrivateObjectiveCard} at the given {@link Integer} index of {@link #deck}.
     * @throws NotValidInputException if the given index is not valid
     */
    public PrivateObjectiveCard getPrivateObjectiveCard(int index) throws NotValidInputException {
        if (index < 0 || index >= deck.size()) {
            throw new NotValidInputException();
        }
        return this.deck.get(index);
    }

    /**
     * @param index : the given {@link Integer} index
     * @return the {@link PrivateObjectiveCard} at the given {@link Integer} index of {@link #deck}
     * and remove it from {@link #deck}
     */
    public PrivateObjectiveCard removePrivateObjectiveCard(int index) {
        if (index < 0 || index > deck.size()) throw new IndexOutOfBoundsException();
        return deck.remove(index);
    }

    /**
     * @return {@link #deck's size}
     */
    public int size() {
        return deck.size();
    }

    /**
     * displays {@link #toString()}.
     */
    public void dump() {
        for (PrivateObjectiveCard c : deck) {
            c.dump();
            System.out.println("\n");
        }
    }

    /**
     * @return the {@link String} representation of this {@link PrivateObjectiveDeck}
     */
    public String toString() {
        String string = new String();
        for (PrivateObjectiveCard c : deck) {
            string = string.concat(c.toString() + "\n\n");
        }
        return string;
    }
}
