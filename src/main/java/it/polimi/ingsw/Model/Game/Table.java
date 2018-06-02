package it.polimi.ingsw.Model.Game;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.ToolCards.ToolCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Table extends Observable implements Serializable {
    private ArrayList<Player> players;
    private RoundTrack roundTrack;
    private DiceBag diceBag;
    private ArrayList<Dice> draftPool;
    private ArrayList<PublicObjectiveCard> gamePOC;
    private ArrayList<ToolCard> gameToolCards;

    public Table() {
        players = new ArrayList<>();
        roundTrack = new RoundTrack();
        diceBag = new DiceBag();
        draftPool = new ArrayList<>();
        gamePOC = new ArrayList<>();
        gameToolCards = new ArrayList<>();
    }

    public String toJson(){
        Table table = new Table();
        table.setPlayers(players);
        table.setRoundTrack(roundTrack);
        table.setDiceBag(diceBag);
        table.setDraftPool(draftPool);
        table.setGamePublicObjectiveCards(gamePOC);
        table.setGameToolCards(gameToolCards);
        return (new Gson()).toJson(table);
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

    public ArrayList<Dice> getDraftPool() {
        return draftPool;
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

    public String toStringDraftPool() {
        String string = new String();
        if(draftPool==null)
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
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
