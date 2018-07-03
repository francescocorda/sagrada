package it.polimi.ingsw.Model.Game;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.SocketVisitor;
import it.polimi.ingsw.view.ViewVisitor;

import java.util.ArrayList;

public class Table extends Observable{
    private ArrayList<Player> players;
    private RoundTrack roundTrack;
    private DiceBag diceBag;
    private ArrayList<Dice> draftPool;
    private ArrayList<PublicObjectiveCard> gamePOC;
    private ArrayList<ToolCard> gameToolCards;
    private Dice activeDice;
    private ToolCard activeToolCard;
    private ScoreTrack scoreTrack;

    public Table() {
        this.players = new ArrayList<>();
        roundTrack = new RoundTrack();
        diceBag = new DiceBag();
        draftPool = new ArrayList<>();
        gamePOC = new ArrayList<>();
        gameToolCards = new ArrayList<>();
        scoreTrack = new ScoreTrack();
        activeDice = null;
        activeToolCard = null;
    }

    public ScoreTrack getScoreTrack() {
        return scoreTrack;
    }

    public void setScoreTrack(ScoreTrack scoreTrack){
        this.scoreTrack = scoreTrack;
    }

    public ToolCard getActiveToolCard() {
        return activeToolCard;
    }

    public void setActiveToolCard(int index) {
        this.activeToolCard = gameToolCards.get(index);
    }

    public void setActiveToolCard(ToolCard activeToolCard) {
        this.activeToolCard = activeToolCard;
    }

    public void removeActiveToolCard() {
        activeToolCard = null;
    }



    public String toJson(){
        Table table = new Table();
        table.setPlayers(players);
        table.setRoundTrack(roundTrack);
        table.setDiceBag(diceBag);
        table.setDraftPool(draftPool);
        table.setGamePublicObjectiveCards(gamePOC);
        table.setGameToolCards(gameToolCards);
        table.setActiveDice(activeDice);
        table.setScoreTrack(scoreTrack);
        return (new Gson()).toJson(table);
    }

    public Player getPlayer(String username) {
        for(Player player: players) {
            if (player.getName().equals(username)) {
                return player;
            }
        }
        return null;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void updateRoundTrack(int indexRT){
        ArrayList<Dice> roundDices = roundTrack.getRoundDices(indexRT);
        roundDices = draftPool;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    public void setDraftPool(int numberOfDice) {
        this.draftPool = diceBag.draw(numberOfDice);
    }

    public void setDraftPool(ArrayList<Dice> draftPool) {
        this.draftPool = draftPool;
    }

    public void rollDraftPool() {
        for (Dice dice: draftPool) {
            dice.roll();
        }
    }

    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    public ArrayList<Dice> cloneDraftPool() {
        ArrayList<Dice> dices = new ArrayList<>();
        for (Dice dice: draftPool) {
            dices.add(new Dice(dice));
        }
        return dices;
    }

    public void insertDiceDraftPool(Dice dice){
        if(dice==null) throw new NullPointerException();
        draftPool.add(dice);
    }

    public Dice removeDiceDraftPool(int index) throws IndexOutOfBoundsException{
        if (index<0 || index> draftPool.size()-1) throw new IndexOutOfBoundsException();
        return draftPool.remove(index);
    }

    public Dice getDiceDraftPool(int index)throws IndexOutOfBoundsException{
        if (index<0 || index> draftPool.size()-1) throw new IndexOutOfBoundsException();
        return draftPool.get(index);
    }

    public ArrayList<PublicObjectiveCard> getGamePublicObjectiveCards() {
        return gamePOC;
    }

    public void setGamePublicObjectiveCards(ArrayList<PublicObjectiveCard> gamePOC) {
        this.gamePOC = gamePOC;
    }

    public ArrayList<ToolCard> getGameToolCards() {
        return gameToolCards;
    }

    public void setGameToolCards(ArrayList<ToolCard> gameToolCards) {
        this.gameToolCards = gameToolCards;
    }

    public Dice getActiveDice() {
        return activeDice;
    }

    public void setActiveDice(Dice activeDice) {
        this.activeDice = activeDice;
    }

    public String toStringDraftPool() {
        String string = new String();
        if(draftPool==null || draftPool.isEmpty())
            string = "NOT ADDED YET";
        else {
            string = "DRAFTPOOL:\n";
            for (Dice dice : draftPool)
                string = string.concat(dice.toString() + " ");
        }
        return string;
    }


    public void dumpDraftPool() {
        System.out.println(toStringDraftPool());
    }

    @Override
    public void display(ViewVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String convert(SocketVisitor visitor) {
        return visitor.visit(this);
    }
}
