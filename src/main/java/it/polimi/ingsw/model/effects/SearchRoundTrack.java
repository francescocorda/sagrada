package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchRoundTrack extends Effect {


    private static String description = "The color of the dice must be present in the round track.";

    /**
     * create a new {@link SearchRoundTrack}
     */
    public SearchRoundTrack() {
        this.stop = false;
        this.commandsLength = 0;
    }

    /**
     * In the first call the effect search if the Round Track contains at least a dice with the same
     * color as the active dice.
     * In the second call the effect checks that the current active dice is of the same color as the
     * one considered in the first call.
     * @param commands is the given {@link ArrayList<String>} of commands needed to apply the effect
     * @param table is the given {@link Table} that the effect modifies using its effects
     * @param round is the given {@link Round} in which the effect acts: some effects need only to know in
     *              which temporal state are of the game, some others actively modify the turns order and/or length
     * @return true if the model manipulation made by the effect is completed correctly
     * @throws RollBackException if the combination of commands given by the caller leads to a state that violates the
     * logic and the effect needs to be applied again with new commands
     * @throws ImpossibleMoveException if the combination of commands given by the caller or the current state
     * of the game leads to a state that do not allow any proceeding
     */
    @Override
    public boolean applyEffect(ArrayList<String> commands, Table table, Round round) throws RollBackException, ImpossibleMoveException {
        Color color = round.getPlayerTurn(0).getColorRoundTrack();
        WindowFrame window = round.getPlayerTurn(0).getPlayer().getWindowFrame();
        if(color == null) {
            HashSet<Color> colorsRT = new HashSet<>();
            for (int i = 0; i < table.getRoundTrack().size(); i++) {
                ArrayList<Dice> roundDices = table.getRoundTrack().getRoundDices(i);
                for (Dice d : roundDices) {
                    colorsRT.add(d.getColor());
                }
            }
            if(colorsRT.contains(table.getActiveDice().getColor())) {
                round.getPlayerTurn(0).setColorRoundTrack(table.getActiveDice().getColor());
                return true;
            } else if (window.containsColors(colorsRT)){
                throw new RollBackException();
            } else {
                throw new ImpossibleMoveException();
            }
        } else {
            if (!table.getActiveDice().getColor().equals(color)) {
                Set<Color> setColor = new HashSet<>();
                setColor.add(round.getPlayerTurn(0).getColorRoundTrack());
                if (window.containsColors(setColor)){
                    if (round.getPlayerTurn(0).getOriginCoordinates().size()>=2) {
                        int size = round.getPlayerTurn(0).getOriginCoordinates().size() - 1;
                        int col = round.getPlayerTurn(0).removeOriginCoordinate(size);
                        int row = round.getPlayerTurn(0).removeOriginCoordinate(size - 1);
                        try {
                            window.enableException("FACE");
                            window.enableException("COLOR");
                            window.enableException("POSITION");
                            window.setDice(row, col, table.getActiveDice());
                        } catch (MismatchedRestrictionException | InvalidNeighboursException | OccupiedCellException | InvalidFirstMoveException e) { }
                    }
                    throw new RollBackException();
                } else {
                    throw new ImpossibleMoveException();
                }
            }
            round.getPlayerTurn(0).setColorRoundTrack(null);
            return true;
        }
    }

    /**
     * explains the effect accordingly to the given parameters.
     * @param table : the given {@link Table} table
     * @param round : the given {@link Round} round
     */
    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + description);
    }

    /**
     * @return the element of the table activated by the effect
     */
    @Override
    public String getActiveTableElement() {
        return "";
    }

}
