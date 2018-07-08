package it.polimi.ingsw.model.game;

import com.google.gson.Gson;
import it.polimi.ingsw.model.cards.public_objectives.PublicObjectiveCard;
import it.polimi.ingsw.model.cards.toolcard.ToolCard;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.socket.SocketVisitor;
import it.polimi.ingsw.view.ViewVisitor;

import java.util.ArrayList;

public class Table extends Observable{
    private ArrayList<Player> players;
    private RoundTrack roundTrack;
    private DiceBag diceBag;
    private ArrayList<Dice> drawPool;
    private ArrayList<PublicObjectiveCard> gamePOC;
    private ArrayList<ToolCard> gameToolCards;
    private Dice activeDice;
    private ToolCard activeToolCard;
    private ScoreTrack scoreTrack;

    /**
     * * creates a new {@link Table}
     */
    public Table() {
        this.players = new ArrayList<>();
        roundTrack = new RoundTrack();
        diceBag = new DiceBag();
        drawPool = new ArrayList<>();
        gamePOC = new ArrayList<>();
        gameToolCards = new ArrayList<>();
        scoreTrack = new ScoreTrack();
        activeDice = null;
        activeToolCard = null;
    }

    /**
     * @return an instance of the table's {@link ScoreTrack}
     */
    public ScoreTrack getScoreTrack() {
        return scoreTrack;
    }

    /**
     * Assign the given parameter to the table
     * @param scoreTrack is the given {@link ScoreTrack}
     */
    public void setScoreTrack(ScoreTrack scoreTrack){
        this.scoreTrack = scoreTrack;
    }

    /**
     * @return an instance of the table's active {@link ToolCard}
     */
    public ToolCard getActiveToolCard() {
        return activeToolCard;
    }

    /**
     * Assign the active tool card given the card's index
     * @param index is the selected card index (from 0 to 2)
     */
    public void setActiveToolCard(int index) {
        this.activeToolCard = gameToolCards.get(index);
    }

    /**
     * Assign the given parameter to the table
     * @param activeToolCard is the given {@link ToolCard}
     */
    public void setActiveToolCard(ToolCard activeToolCard) {
        this.activeToolCard = activeToolCard;
    }

    /**
     * remove the active tool card from the table
     */
    public void removeActiveToolCard() {
        activeToolCard = null;
    }

    /**
     * @return a deep copy of the table
     */
    public Table copy() {
        Gson gson = new Gson();
        String table = gson.toJson(this);
        Table tempTable = gson.fromJson(table, Table.class);
        return tempTable;
    }

    /**
     * @param username is the given player's username
     * @return
     */
    public Player getPlayer(String username) {
        for(Player player: players) {
            if (player.getName().equals(username)) {
                return player;
            }
        }
        return null;
    }

    /**
     * @return an {@link ArrayList<Player>} of the table's {@link Player}
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * set the {@link ArrayList<Player>} to the table
     * @param players is the given {@link ArrayList<Player>}
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Update the round track given the round index
     * @param indexRT is the given round index
     */
    public void updateRoundTrack(int indexRT){
        ArrayList<Dice> roundDices = roundTrack.getRoundDices(indexRT);
        roundDices = drawPool;
    }

    /**
     * @return an instance of the table's {@link RoundTrack}
     */
    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    /**
     * set the {@link RoundTrack} to the table
     * @param roundTrack is the given {@link RoundTrack}
     */
    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    /**
     * @return an instance of the table's {@link DiceBag}
     */
    public DiceBag getDiceBag() {
        return diceBag;
    }

    /**
     * sets the table dice bad
     * @param diceBag is the given {@link DiceBag}
     */
    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    /**
     * sets the table's draw pool
     * @param numberOfDice is the number of dice to draw into the draw pool
     */
    public void setDrawPool(int numberOfDice) {
        this.drawPool = diceBag.draw(numberOfDice);
    }

    /**
     * sets the table's draw pool
     * @param drawPool is the {@link ArrayList<Dice>}
     */
    public void setDrawPool(ArrayList<Dice> drawPool) {
        this.drawPool = drawPool;
    }

    /**
     * rolls the table's draw pool
     */
    public void rollDrawPool() {
        for (Dice dice: drawPool) {
            dice.roll();
        }
    }

    /**
     * @return an instance of the table's {@link ArrayList<Dice>} draw pool
     */
    public ArrayList<Dice> getDrawPool() {
        return drawPool;
    }

    /**
     * @return a copy of the table's draw pool
     */
    public ArrayList<Dice> cloneDrawPool() {
        ArrayList<Dice> dices = new ArrayList<>();
        for (Dice dice: drawPool) {
            dices.add(new Dice(dice));
        }
        return dices;
    }

    /**
     * @return the {@link ArrayList<PublicObjectiveCard>} of the public objective cards used in the game
     */
    public ArrayList<PublicObjectiveCard> getGamePublicObjectiveCards() {
        return gamePOC;
    }

    /**
     * @return the {@link ArrayList<ToolCard>} of the tool cards used in the game
     */
    public ArrayList<ToolCard> getGameToolCards() {
        return gameToolCards;
    }

    /**
     * @return the table's active {@link Dice}
     */
    public Dice getActiveDice() {
        return activeDice;
    }

    /**
     * set the table's active {@link Dice}
     * @param activeDice is the given active {@link Dice}
     */
    public void setActiveDice(Dice activeDice) {
        this.activeDice = activeDice;
    }

    /**
     * @return the representation of the Draw Pool
     */
    public String toStringDraftPool() {
        String string = new String();
        if(drawPool ==null || drawPool.isEmpty())
            string = "NOT ADDED YET";
        else {
            string = "DRAFTPOOL:\n";
            for (Dice dice : drawPool)
                string = string.concat(dice.toString() + " ");
        }
        return string;
    }

    /**
     * displays the table's Draw Pool.
     */
    public void dumpDraftPool() {
        System.out.println(toStringDraftPool());
    }

    /**
     * accept a ViewVisitor
     * @param visitor : the given {@link ViewVisitor} visitor
     */
    @Override
    public void display(ViewVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * accept a SocketVisitor
     * @param visitor : the given {@link SocketVisitor} visitor
     * @return the result of the visitor's visit method
     */
    @Override
    public String convert(SocketVisitor visitor) {
        return visitor.visit(this);
    }
}
