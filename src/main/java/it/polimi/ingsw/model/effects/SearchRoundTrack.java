package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchRoundTrack extends Effect {


    private static String description = "The color of the dice must be present in the round track.";

    public SearchRoundTrack() {
        this.stop = false;
        this.commandsLenght = 0;
    }

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

    @Override
    public void explainEffect(Table table, Round round) {
        table.notifyObservers(round.getCurrentPlayer().getName() + "'s turn: " + description);
    }

    @Override
    public String getActiveTableElement() {
        return "";
    }

}
