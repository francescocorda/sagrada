package it.polimi.ingsw.model.cards.toolcard;


import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.exceptions.ImpossibleMoveException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.exceptions.RollBackException;

import java.io.Serializable;
import java.util.ArrayList;

import static it.polimi.ingsw.model.game.Game.INVALID_MOVE_BY_PLAYER;

public class ToolCard implements Serializable {

    private transient static final int INITIAL_PRICE = 1;
    private transient static final int ORDINARY_PRICE = 2;
    private int ID;
    private String name;
    private String description;
    private int numOfTokens;
    private int price;
    private transient ArrayList<Integer> usableInTurns;
    private transient ArrayList<Integer> movesLeft;
    private transient ArrayList<Effect> effects;
    private transient ArrayList<Integer> stops;
    private transient int index;
    private transient int stopIndex;
    private transient int stop;

    /**
     * creates a new {@link ToolCard} from the given parameters.
     * @param ID : is the given int ID
     * @param name : is the given {@link String} name
     * @param description : is the given {@link String} description
     * @param usableInTurns : is the given {@link ArrayList<Integer>} usableInTurns
     * @param movesLeft : is the given {@link ArrayList<Integer>} movesLeft
     */
    public ToolCard(int ID, String name, String description, ArrayList<Integer> usableInTurns, ArrayList<Integer> movesLeft) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.numOfTokens = 0;
        this.price = INITIAL_PRICE;
        this.usableInTurns = usableInTurns;
        this.movesLeft = movesLeft;
        this.effects = new ArrayList<>();
        this.index = 0;
        this.stopIndex = 0;
        this.stop = 0;
        this.stops = new ArrayList<>();
    }

    /**
     * The method apply the tool card effect until another input is needed to proceed further.
     * The tool card effects that need an input from the controller are identified as stops; only one stop effect is
     * executed at every method call.
     * When the input violates the game rules, the current effect returns a false result. The execution does
     * not proceed until an input that respect the game logic is received.
     * When the tool card use is completed, {@link #resetToolCard(Table, Round)} is called.
     * @param commands is the given {@link ArrayList<String>} of commands that the tool card uses as base for its
     *                 operations
     * @param table is the given {@link Table} that the tool card modifies using its effects
     * @param round is the given {@link Round} in which the tool card acts: some tool cards need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @throws ImpossibleMoveException if the combination of commands given by the controller or the current state
     * of the game leads to a state do not allow any proceeding
     */
    public void useToolCard(ArrayList<String> commands, Table table, Round round) throws ImpossibleMoveException {
        boolean result = true;
        while (index<effects.size() && index<stop && result) {
            try {
                if (effects.get(index).applyEffect(commands, table, round)) {
                    if (index + 1 < effects.size()) {
                        effects.get(index + 1).explainEffect(table, round);
                    }
                    index++;
                } else {
                    effects.get(index).explainEffect(table, round);
                    result = false;
                }
            } catch (RollBackException e) {
                table.notifyObservers(INVALID_MOVE_BY_PLAYER + round.getCurrentPlayer().getName() +
                        ":\n" + e.getMessage());
                index--;
                effects.get(index).explainEffect(table, round);
                result = false;
            }
        }

        if (!result) {
            return;
        }
        if (index == stop) {
            nextStop();
        }
        if(index == effects.size()) {
            resetToolCard(table, round);
            round.getPlayerTurn(0).setToolCardActive(false);
            round.getPlayerTurn(0).setToolCardUsed(true);
        }
    }


    /**
     *The method resets the tool card and prepares it to the next use in the game.
     * @param table is the given {@link Table} that the tool card modifies using its effects
     * @param round is the given {@link Round} in which the tool card acts
     */
    public void resetToolCard(Table table, Round round) {
        index = 0;
        stopIndex = 0;
        stop = 0;
        table.removeActiveToolCard();
        round.getPlayerTurn(0).setDraftPoolSize(0);
        round.getPlayerTurn(0).getOriginCoordinates().clear();
        round.getPlayerTurn(0).setColorRoundTrack(null);
    }

    /**
     * sets {@link #stop} to it's next value.
     */
    public void nextStop() {
        if (stopIndex+1<stops.size()) {
            stopIndex++;
            stop = stops.get(stopIndex);
        }
    }

    /**
     * moves the indexes to end of the effect's list
     */
    public void endUse() {
        index = effects.size()-1;
        stopIndex = effects.size();
        stop = effects.size();
    }

    /**
     * The method iterate over a list of effects and saves the position of the ones that are "stops":
     * An effect is a stop if it needs an external input to operate.
     * @param effects is the {@link ArrayList<Effect>} of effects
     */
    public void generateStops(ArrayList<Effect> effects) {
        this.stops = new ArrayList<>();
        for (int i = 0; i < effects.size(); i++) {
            if (effects.get(i).isStop()) {
                this.stops.add(i);
            }
        }
        this.stops.add(effects.size());
        this.stopIndex = 0;
        if (!stops.isEmpty()) {
            this.stop = this.stops.get(0);
        }
    }

    /**
     * @return the {@link String} ActiveTableElement.
     */
    public String getActiveTableElement() {
        if (index < effects.size())
            return effects.get(index).getActiveTableElement();
        else
            return "";
    }

    /**
     * @return the length of Commands.
     */
    public int getCommandsLength() {
        return effects.get(index).getCommandsLength();
    }

    /**
     * @return {@link #effects}.
     */
    public ArrayList<Effect> getEffects() {
        return effects;
    }

    /**
     * sets {@link #effects} as the given {@link ArrayList<Effect>} effects.
     * @param effects : the given {@link ArrayList<Effect>} effects
     */
    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
        generateStops(effects);
    }

    /**
     * @return {@link #ID}.
     */
    public int getID() {
        return ID;
    }

    /**
     * sets {@link #ID} as the given int ID.
     * @param ID : the given int ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * sets {@link #name} as the given {@link String} name.
     * @param name : the given {@link String} name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link #description}.
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets {@link #description} as the given {@link String} description.
     * @param description : the given {@link String} description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return {@link #numOfTokens}.
     */
    public int getNumOfTokens() {
        return numOfTokens;
    }

    /**
     * sets {@link #numOfTokens} as the given int numOfTokens.
     * @param numOfTokens : the given int numOfTokens
     */
    public void setNumOfTokens(int numOfTokens) {
        this.numOfTokens = numOfTokens;
    }

    /**
     * @return {@link #usableInTurns}.
     */
    public ArrayList<Integer> getUsableInTurns() {
        return usableInTurns;
    }

    /**
     * sets {@link #usableInTurns} as the given {@link ArrayList<Integer>}.
     * @param usableInTurns : the given {@link ArrayList<Integer>}
     */
    public void setUsableInTurns(ArrayList<Integer> usableInTurns) {
        this.usableInTurns = usableInTurns;
    }

    /**
     * @return {@link #movesLeft}.
     */
    public ArrayList<Integer> getMovesLeft() {
        return movesLeft;
    }

    /**
     * sets {@link #movesLeft} as the given {@link ArrayList<Integer>}.
     * @param movesLeft : the given {@link ArrayList<Integer>}
     */
    public void setMovesLeft(ArrayList<Integer> movesLeft) {
        this.movesLeft = movesLeft;
    }

    /**
     * @param player : the given {@link Player} player
     * @return true if the given {@link Player} player can afford to buy this {@link ToolCard},
     * false otherwise.
     */
    public boolean payTokens(Player player) {
        if (player.getNumOfTokens() >= price) {
            numOfTokens += price;
            try {
                player.setNumOfTokens(player.getNumOfTokens()- price);
            } catch (NotValidInputException e) {
            }
            price = ORDINARY_PRICE;
            return true;
        }
        return false;
    }

    /**
     *refunds the given {@link Player} player's tokens.
     * @param player : the given {@link Player} player
     */
    public void refundTokens(Player player) {
        int refund = 0;
        if (numOfTokens >= INITIAL_PRICE) {
            refund = INITIAL_PRICE;
            if (numOfTokens >= ORDINARY_PRICE) {
                refund = ORDINARY_PRICE;
            }
        }

        numOfTokens = numOfTokens - refund;
        if (numOfTokens==0)
            price = INITIAL_PRICE;
        try {
            player.setNumOfTokens(player.getNumOfTokens() + refund);
        } catch (NotValidInputException e) {
            //
        }
    }

    /**
     * @param turn : the given {@link PlayerTurn} turn
     * @return if at the given {@link PlayerTurn} turn this {@link ToolCard} is allowed or not.
     */
    public boolean useAllowed(PlayerTurn turn) {
        return usableInTurns.contains(turn.getTurnNumber())
                && movesLeft.contains(turn.getMovesLeft())
                && !turn.isToolCardUsed();
    }

    /**
     * @return the {@link String} representation of this {@link ToolCard}.
     */
    @Override
    public String toString() {
        return  "\nID: " + ID + "\n" +
                "Name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Number Of Tokens: " + numOfTokens + "\n" +
                "Price: " + price + "\n";
    }

    /**
     * displays {@link #toString()}.
     */
    public void dump() {
        System.out.println(toString());
    }
}
