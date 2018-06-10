package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveDeck;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Random;

import static it.polimi.ingsw.Model.Game.RoundTrack.NUMBER_OF_ROUND;

public class Game implements Serializable {
    int matchID;
    private ArrayList<Player> players;
    private PrivateObjectiveDeck privateObjectiveDeck;
    private PublicObjectiveDeck publicObjectiveDeck;
    private PatternDeck patternDeck;
    private ArrayList<PatternCard> matchPatternDeck;
    private ArrayList<ToolCard> toolCards;
    private ArrayList<Round> rounds;
    private Table table;
    private Move move;
    public static final String INVALID_MOVE_BY_PLAYER = "Invalid move by player ";
    public static final String PLAY_GAME = "Play Game.";
    public static final int PUB_OBJ_CARDS_DIMENSION = 3;
    public static final int TOOL_CARDS_DIMENSION = 3;
    public static final int PROPOSED_PATTERNS = 4;


    public Game(int matchID, ArrayList<String> names ){
        this.matchID = matchID;
        this.players = new ArrayList<>();
        for(int i=0; i<names.size(); i++){
            this.players.add(new Player(names.get(i)));
        }
        privateObjectiveDeck = new PrivateObjectiveDeck();
        patternDeck = null;
        publicObjectiveDeck = null;
        matchPatternDeck = new ArrayList<>();
        rounds = new ArrayList<>();
        toolCards = new ArrayList<>();
        this.table = new Table();
        move = null;
        table.setDiceBag(new DiceBag());
        table.setRoundTrack(new RoundTrack());
        table.setPlayers(players);
        for (int i = 0; i < NUMBER_OF_ROUND; i++) {
            rounds.add(new Round(this.players,i%players.size()));
        }
    }

    public ArrayList<String> getUserNames() {
        ArrayList<String> userNames = new ArrayList<>();
        for (Player player: players) {
            userNames.add(player.getName());
        }
        return userNames;
    }

    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    public void setToolCards(ArrayList<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    public void setPatternDeck(ArrayList<PatternCard> deck){
        patternDeck = new PatternDeck(deck);
    }

    public void setPublicObjectiveDeck(ArrayList<PublicObjectiveCard> deck){
        publicObjectiveDeck = new PublicObjectiveDeck(deck);
    }

    public void drawDices() {
        table.setDraftPool(2*players.size()+1);
    }

    public PrivateObjectiveCard assignPrivateObjectiveCard(String player) throws NotValidInputException {
        int i=0;
        while(!players.get(i).getName().equals(player)&& i<players.size()){
            i++;
        }
        if(i == players.size()){
            throw new NotValidInputException();
        } else {
            Random rand = new Random();
            int index = rand.nextInt(privateObjectiveDeck.size());
            players.get(i).setPrivateObjectiveCard(privateObjectiveDeck.removePrivateObjectiveCard(index));
            return players.get(i).getPrivateObjectiveCard();
        }
    }

    public void drawPublicObjectiveCards() {
        Random rand = new Random();
        for (int i = 0; i < PUB_OBJ_CARDS_DIMENSION; i++) {
            int index = rand.nextInt(publicObjectiveDeck.size());
            table.getGamePublicObjectiveCards().add(publicObjectiveDeck.removePuOC(index));
        }
    }

    public void drawToolCards() {
        Random rand = new Random();
        for (int i = 0; i < TOOL_CARDS_DIMENSION; i++) {
            int index = rand.nextInt(toolCards.size());
            table.getGameToolCards().add(toolCards.remove(index));
        }
    }

    public ArrayList<PatternCard> drawPatternCards() {
        ArrayList<PatternCard> patterns = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < PROPOSED_PATTERNS; i++) {
            int index = rand.nextInt(patternDeck.size());
            try {
                patterns.add(patternDeck.getPatternCard(index));
            } catch (NotValidInputException e) {
                e.printStackTrace();
            }
            matchPatternDeck.add(patternDeck.removePatternCard(index));
        }
        return patterns;
    }

    public void setPatternCard(String player, int patternIndex) throws NotValidInputException {
        int i=0;
        while(!players.get(i).getName().equals(player)&& i<players.size()){
            i++;
        }
        if(i==players.size()){
            throw new NotValidInputException();
        } else {
            if(players.get(i).getPatternCard() == null) {
                players.get(i).setPatternCard(matchPatternDeck.get(PROPOSED_PATTERNS*i + patternIndex));
                players.get(i).setNumOfTokens(matchPatternDeck.get(PROPOSED_PATTERNS*i + patternIndex).getDifficulty());
            } else {
                throw new NotValidInputException();
            }
        }
    }

    public boolean doneAssignPatternCards() {
        for (Player player: players) {
            if(player.getPatternCard() == null) {
                return false;
            }
        }
        table.notifyObservers();
        table.notifyObservers(PLAY_GAME);
        return true;
    }

    public String getCurrentPlayer() {
        return rounds.get(0).getCurrentPlayer().getName();
    }

    public boolean isCurrentPlayer(String name) {
        if(rounds.get(0).getPlayerTurn(0).getPlayer().getName().equals(name)) {
            return true;
        } else
            return false;
    }

    public void performMove(ArrayList<String> commands) {
            if(rounds.get(0).getPlayerTurn(0).getMovesLeft() > 0) {
                rounds.get(0).getPlayerTurn(0).setMoveActive(true);
                move.performMove(commands);
            }
    }

    public void createMove() {
        move = new Move(table, rounds.get(0));
        move.explainEffect(table, rounds.get(0));
    }

    public boolean moveAllowed() {
        if (rounds.get(0).getPlayerTurn(0).getMovesLeft()>0) {
            table.notifyObservers(getCurrentPlayer() + "'s turn: move allowed.");
            return true;
        }
        table.notifyObservers(getCurrentPlayer() + "'s turn: move not allowed.");
        return false;
    }

    public boolean toolCardUseAllowed(int indexTC) {
        if(table.getGameToolCards().get(indexTC).useAllowed(rounds.get(0).getPlayerTurn(0))) {
            table.notifyObservers(getCurrentPlayer() + "'s turn: tool card " + table.getGameToolCards().get(indexTC).getName() + " use allowed.");
            return true;
        }
        table.notifyObservers(getCurrentPlayer() + "'s turn: tool card " + table.getGameToolCards().get(indexTC).getName() + " use not allowed.");
        return false;
    }

    public boolean buyToolCard(int indexTC) {
        if(table.getGameToolCards().get(indexTC).payTokens(rounds.get(0).getPlayerTurn(0).getPlayer())) {
            rounds.get(0).getPlayerTurn(0).setToolCardActive(true);
            table.setActiveToolCard(indexTC);
            table.notifyObservers(getCurrentPlayer() + "'s turn: acquired tool card " + table.getGameToolCards().get(indexTC).getName());
            table.getActiveToolCard().getEffects().get(0).explainEffect(table, rounds.get(0));
            return true;
        }
        return false;
    }

    public int getToolCardCommandsSize() {
        return table.getActiveToolCard().getCommandsLenght();
    }

    public int getMoveCommandsSize() {
        return move.getCommandsLenght();
    }

    public void useToolCard(ArrayList<String> commands) {
        table.getActiveToolCard().useToolCard(commands, table, rounds.get(0));
    }

    public void skipTurn() {
        if(rounds.get(0).getPlayerTurn(0) != null) {
            rounds.get(0).getPlayerTurn(0).setMovesLeft(0);
            rounds.get(0).getPlayerTurn(0).setToolCardUsed(true);
            table.notifyObservers(getCurrentPlayer() + "'s turn: Turn skipped.");
        }
    }

    public void countScores(){
        int score;
        for(Player player: table.getPlayers()){
            score = 0;
            score += player.getPrivateObjectiveCard().countScore(player.getWindowFrame());
            score += player.getNumOfTokens();

            for(PublicObjectiveCard pubObjCard : table.getGamePublicObjectiveCards()){
                score += pubObjCard.countScore(player.getWindowFrame());
            }
            player.setScore(score);
        }
    }

    public void broadcastRankings(){
        int score;
        ArrayList<Player> players;
        for (int i = 0; i < table.getPlayers().size(); i++) {
            for(Player player: table.getPlayers()){

            }
        }
    }

    public boolean isGameEnded() {
        if (rounds.isEmpty()) {
            table.notifyObservers("Game end.");
            return true;
        } else {
            return false;}
    }

    public boolean isRoundEnded() {
        if (rounds.get(0).size()==0) {
            table.getRoundTrack().setRoundDices(table.getDraftPool(), 10-rounds.size());
            rounds.remove(0);
            table.getDraftPool().clear();
            drawDices();
            table.notifyObservers();
            table.notifyObservers("New round, draft pool extracted.\nNewTurn.");
            return true;
        }
        return false;
    }
    public boolean isTurnEnded() {
        if (rounds.get(0).getPlayerTurn(0).isEnded()) {
            if(table.getActiveToolCard() != null) {
                table.getActiveToolCard().resetToolCard(table, rounds.get(0));
                table.removeActiveToolCard();
            }
            if(table.getActiveDice() != null) {
                table.getDraftPool().add(table.getActiveDice());
                table.setActiveDice(null);
            }
            rounds.get(0).removeTurn(0);
            table.notifyObservers("New Turn.");
            return true;
        }
        return false;
    }

    public boolean isMoveActive() {
        PlayerTurn playerTurn = rounds.get(0).getPlayerTurn(0);
        if (playerTurn.isMoveActive()) {
            return true;
        }
        return false;
    }

    public boolean isToolCardActive() {
        PlayerTurn playerTurn = rounds.get(0).getPlayerTurn(0);
        return playerTurn.isToolCardActive();
    }

    public boolean isToolCardUsed() {
        return rounds.get(0).getPlayerTurn(0).isToolCardUsed();
    }

    public void addObserver (Observer o) {
        table.addObserver(o);
    }

    public Table getTable() {
        return table;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

}
