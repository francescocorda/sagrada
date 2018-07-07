package it.polimi.ingsw.model.game;

import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class PlayerTurn {

    public static final int MOVES = 1;

    private final  int turnNumber;
    private Player player;
    private int movesLeft;
    private boolean toolCardUsed;
    private ArrayList<Integer> originCoordinates;
    private Color colorRoundTrack;
    private boolean skipEffect;
    private boolean moveActive;
    private boolean toolCardActive;
    private int draftPoolSize;

    /**
     * creates a new {@link PlayerTurn} from the given parameters.
     * @param player : the given {@link Player}
     * @param turnNumber : the given int turnNumber
     */
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

    /**
     * @return {@link #draftPoolSize}.
     */
    public int getDraftPoolSize() {
        return draftPoolSize;
    }

    /**
     * sets {@link #draftPoolSize} as the given int draftPoolSize.
     * @param draftPoolSize : the given int draftPoolSize
     */
    public void setDraftPoolSize(int draftPoolSize) {
        this.draftPoolSize = draftPoolSize;
    }

    /**
     * @return {@link #moveActive}.
     */
    public boolean isMoveActive() {
        return moveActive;
    }

    /**
     * sets {@link #moveActive} as the given {@link Boolean} moveActive.
     * @param moveActive : the given {@link Boolean} moveActive
     */
    public void setMoveActive(boolean moveActive) {
        this.moveActive = moveActive;
    }

    /**
     * @return {@link #toolCardActive}.
     */
    public boolean isToolCardActive() {
        return toolCardActive;
    }

    /**
     * sets {@link #toolCardActive} as the given {@link Boolean} toolCardActive.
     * @param toolCardActive : the given {@link Boolean} toolCardActive
     */
    public void setToolCardActive(boolean toolCardActive) {
        this.toolCardActive = toolCardActive;
    }

    /**
     * @return {@link #colorRoundTrack}.
     */
    public Color getColorRoundTrack() {
        return colorRoundTrack;
    }

    /**
     * sets {@link #colorRoundTrack} as the given {@link Color} colorRoundTrack.
     * @param colorRoundTrack : the given {@link Color} colorRoundTrack
     */
    public void setColorRoundTrack(Color colorRoundTrack) {
        this.colorRoundTrack = colorRoundTrack;
    }

    /**
     * @return {@link #skipEffect}.
     */
    public boolean isSkipEffect() {
        return skipEffect;
    }

    /**
     * sets {@link #skipEffect} as the given {@link Boolean} skipEffect.
     * @param skipEffect : the given {@link Boolean} skipEffect
     */
    public void setSkipEffect(boolean skipEffect) {
        this.skipEffect = skipEffect;
    }

    /**
     * @return {@link #originCoordinates}.
     */
    public ArrayList<Integer> getOriginCoordinates() {
        return originCoordinates;
    }

    /**
     * @param index : the given int index
     * @return the int value of {@link #originCoordinates} at the given int index.
     */
    public int getOriginCoordinate(int index) {
        return originCoordinates.get(index);
    }

    /**
     * @param index : the given int index
     * @return the int value of {@link #originCoordinates} at the given int index, then removes it.
     */
    public int removeOriginCoordinate(int index) {
        return originCoordinates.remove(index);
    }

    /**
     * @return last value of {@link #originCoordinates}, then removes it.
     */
    public int removeOriginCoordinate() {
        return originCoordinates.remove(originCoordinates.size()-1);
    }

    /**
     * sets {@link #originCoordinates} as the given {@link ArrayList<Integer>} originCoordinates.
     * @param originCoordinates : the given {@link ArrayList<Integer>} originCoordinates
     */
    public void setOriginCoordinates(ArrayList<Integer> originCoordinates) {
        this.originCoordinates = originCoordinates;
    }

    /**
     * adds the given int originCoordinate to {@link #originCoordinates}.
     * @param originCoordinate : the given int originCoordinate
     */
    public void addOriginCoordinate(int originCoordinate) {
        originCoordinates.add(originCoordinate);
    }

    /**
     * @return {@link #turnNumber}.
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * @return {@link #player}.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * sets {@link #player} as the given {@link Player} player.
     * @param player : the given {@link Player} player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return {@link #movesLeft}.
     */
    public int getMovesLeft() {
        return movesLeft;
    }

    /**
     * sets {@link #movesLeft} as the given int movesLeft.
     * @param movesLeft : the given int movesLeft
     */
    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    /**
     * @return {@link #toolCardUsed}
     */
    public boolean isToolCardUsed() {
        return toolCardUsed;
    }

    /**
     * sets {@link #toolCardUsed} as the given {@link Boolean} toolCardUsed.
     * @param toolCardUsed : the given {@link Boolean} toolCardUsed
     */
    public void setToolCardUsed(boolean toolCardUsed) {
        this.toolCardUsed = toolCardUsed;
    }

    /**
     * pays the given amount: int toPay.
     * @param toPay : the given amount: int toPay
     * @throws NotValidInputException if the given amount to pay is not allowed
     */
    public void payTokens(int toPay) throws  NotValidInputException{
        player.setNumOfTokens(player.getNumOfTokens()-toPay);
    }

    /**
     * @return whether this {@link PlayerTurn} is ended or not.
     */
    public boolean isEnded() {
        if (movesLeft==0 && toolCardUsed) {
            return true;
        }
        return false;
    }

    /**
     * @return the {@link String} representation of this {@link PlayerTurn}.
     */
    @Override
    public String toString() {
        return "PlayerTurn{" +
                "turnNumber=" + turnNumber +
                ", player=" + player.getName() +
                ", movesLeft=" + movesLeft +
                ", toolCardUsed=" + toolCardUsed +
                '}';
    }

    /**
     * displays {@link #toString()}.
     */
    public void dump(){
        System.out.println(toString());
    }
}