package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerTurn implements Serializable {

    public static final int MOVES = 1;

    final private int turnNumber;
    private Player player;
    private int movesLeft;
    private boolean toolCardUsed;
    private ArrayList<Integer> originCoordinates;
    private Color colorRoundTrack;
    private boolean skipEffect;
    private boolean moveActive;
    private boolean toolCardActive;
    private int draftPoolSize;

    public PlayerTurn(Player player, int turnNumber) {
        this.player = player;
        this.turnNumber = turnNumber;
        movesLeft = MOVES;
        toolCardUsed = false;
        originCoordinates = new ArrayList<>();
        colorRoundTrack = null;
        skipEffect = false;
        moveActive = false;
        toolCardActive = false;
        draftPoolSize = 0;
    }

    public int getDraftPoolSize() {
        return draftPoolSize;
    }

    public void setDraftPoolSize(int draftPoolSize) {
        this.draftPoolSize = draftPoolSize;
    }

    public boolean isMoveActive() {
        return moveActive;
    }

    public void setMoveActive(boolean moveActive) {
        this.moveActive = moveActive;
    }

    public boolean isToolCardActive() {
        return toolCardActive;
    }

    public void setToolCardActive(boolean toolCardActive) {
        this.toolCardActive = toolCardActive;
    }

    public Color getColorRoundTrack() {
        return colorRoundTrack;
    }

    public void setColorRoundTrack(Color colorRoundTrack) {
        this.colorRoundTrack = colorRoundTrack;
    }

    public boolean isSkipEffect() {
        return skipEffect;
    }

    public void setSkipEffect(boolean skipEffect) {
        this.skipEffect = skipEffect;
    }

    public ArrayList<Integer> getOriginCoordinates() {
        return originCoordinates;
    }

    public int getOriginCoordinate(int index) {
        return originCoordinates.get(index);
    }

    public int removeOriginCoordinate(int index) {
        return originCoordinates.remove(index);
    }

    public int removeOriginCoordinate() {
        return originCoordinates.remove(originCoordinates.size()-1);
    }

    public void setOriginCoordinates(ArrayList<Integer> originCoordinates) {
        this.originCoordinates = originCoordinates;
    }

    public void addOriginCoordinate(int originCoordinate) {
        originCoordinates.add(originCoordinate);
    }


    public int getTurnNumber() {
        return turnNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    public boolean isToolCardUsed() {
        return toolCardUsed;
    }

    public void setToolCardUsed(boolean toolCardUsed) {
        this.toolCardUsed = toolCardUsed;
    }

    public void payTokens(int toPay) throws  NotValidInputException{
        player.setNumOfTokens(player.getNumOfTokens()-toPay);
    }

    public boolean isEnded() {
        if (movesLeft==0 && toolCardUsed) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "PlayerTurn{" +
                "turnNumber=" + turnNumber +
                ", player=" + player +
                ", movesLeft=" + movesLeft +
                ", toolCardUsed=" + toolCardUsed +
                '}';
    }


    public void dump(){
        System.out.println(toString());
    }


}