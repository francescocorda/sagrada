package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveDeck;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.client.GUI.login.LoginManager;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.observer.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.Model.Game.RoundTrack.NUMBER_OF_ROUNDS;

public class Game {
    private static final Logger logger = Logger.getLogger(Game.class.getName());
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
    private ArrayList<Dice> old_draftPool;
    private WindowFrame old_windowFrame;
    private DiceBag old_diceBag;
    private RoundTrack old_roundTrack;
    private ArrayList<PlayerTurn> old_playerTurns;


    public Game(int matchID, ArrayList<String> names) {
        this.matchID = matchID;
        this.players = new ArrayList<>();
        for (int i=0; i<names.size(); i++){
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
        for (int i = 0; i < NUMBER_OF_ROUNDS; i++) {
            rounds.add(new Round(this.players,i%players.size()));
        }
        ArrayList<PlayerTurn> lastRound = new ArrayList<>(rounds.get(NUMBER_OF_ROUNDS-1).getPlayerTurns());
        table.getScoreTrack().setLastRound(lastRound);

        old_draftPool = null;
        old_windowFrame = null;
        old_diceBag = null;
        old_roundTrack = null;
        old_playerTurns = null;
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

    public void setPatternDeck(PatternDeck deck){
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

    public boolean setPatternCard(String player, int patternIndex) {
        int i=0;
        while(!players.get(i).getName().equals(player)&& i<players.size()){
            i++;
        }
        if(i==players.size()){
            return false;
        } else {
            if(players.get(i).getPatternCard() == null) {
                players.get(i).setPatternCard(matchPatternDeck.get(PROPOSED_PATTERNS*i + patternIndex));
                try {
                    players.get(i).setNumOfTokens(matchPatternDeck.get(PROPOSED_PATTERNS*i + patternIndex).getDifficulty());
                } catch (NotValidInputException e) {
                    logger.log(Level.SEVERE, e.toString());
                }
                return true;
            } else {
                return false;
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
        if(!isGameEnded()) return rounds.get(0).getCurrentPlayer().getName();
        else return null;
    }

    public boolean isCurrentPlayer(String name) {
        if(rounds.get(0).getPlayerTurn(0).getPlayer().getName().equals(name)) {
            return true;
        } else
            return false;
    }

    public void performMove(ArrayList<String> commands) {
        if(rounds.get(0).getPlayerTurn(0).getMovesLeft() > 0) {
            try {
                move.performMove(commands);
            } catch (ImpossibleMoveException e) {
                cancelMove();
            }
        }
    }

    public void createMove() {
        move = new Move(table, rounds.get(0));
        saveGame();
        rounds.get(0).getPlayerTurn(0).setMoveActive(true);
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

    public void saveGame() {
        this.old_draftPool = table.cloneDraftPool();
        this.old_windowFrame = new WindowFrame(rounds.get(0).getCurrentPlayer().getWindowFrame());
        this.old_diceBag = new DiceBag(table.getDiceBag());
        this.old_roundTrack = new RoundTrack(table.getRoundTrack());
        this.old_playerTurns = new ArrayList<>(rounds.get(0).getPlayerTurns());
    }

    public void cancelToolCardUse() {
        if (rounds.get(0).getPlayerTurn(0).isToolCardActive()) {
            table.setDraftPool(old_draftPool);
            table.setRoundTrack(old_roundTrack);
            table.setDiceBag(old_diceBag);
            table.setActiveDice(null);
            rounds.get(0).setPlayerTurns(old_playerTurns);
            rounds.get(0).getCurrentPlayer().setWindowFrame(old_windowFrame);
            rounds.get(0).getPlayerTurn(0).setToolCardActive(false);
            rounds.get(0).getPlayerTurn(0).setToolCardUsed(false);
            table.getActiveToolCard().refundTokens(rounds.get(0).getCurrentPlayer());
            table.getActiveToolCard().resetToolCard(table, rounds.get(0));
            table.removeActiveToolCard();
            table.notifyObservers();
        }
    }

    public void cancelMove() {
        if(rounds.get(0).getPlayerTurn(0).isMoveActive()) {
            rounds.get(0).getPlayerTurn(0).setMoveActive(false);
            table.setDraftPool(old_draftPool);
            table.setActiveDice(null);
            rounds.get(0).getCurrentPlayer().setWindowFrame(old_windowFrame);
            table.notifyObservers();
        }
    }

    public boolean toolCardUseAllowed(int indexTC) {
        if(table.getGameToolCards().get(indexTC).useAllowed(rounds.get(0).getPlayerTurn(0))) {
            table.notifyObservers(getCurrentPlayer() + "'s turn: tool card " + table.getGameToolCards().get(indexTC).getName() + " can be used.");
            return true;
        }
        table.notifyObservers(getCurrentPlayer() + "'s turn: tool card " + table.getGameToolCards().get(indexTC).getName() + " can't be used in this moment.");
        return false;
    }

    public boolean buyToolCard(int indexTC) {
        if(table.getGameToolCards().get(indexTC).payTokens(rounds.get(0).getPlayerTurn(0).getPlayer())) {
            rounds.get(0).getPlayerTurn(0).setToolCardActive(true);
            table.setActiveToolCard(indexTC);
            saveGame();
            table.notifyObservers(getCurrentPlayer() + "'s turn: acquired tool card " + table.getGameToolCards().get(indexTC).getName());
            table.getActiveToolCard().getEffects().get(0).explainEffect(table, rounds.get(0));
            return true;
        }
        table.notifyObservers(getCurrentPlayer() + ": unable to buy it, not enough tokens.");
        return false;
    }

    public int getToolCardCommandsSize() {
        return table.getActiveToolCard().getCommandsLenght();
    }

    public int getMoveCommandsSize() {
        return move.getCommandsLenght();
    }

    public void useToolCard(ArrayList<String> commands) {
        try {
            table.getActiveToolCard().useToolCard(commands, table, rounds.get(0));
        } catch (ImpossibleMoveException e) {
            table.notifyObservers(INVALID_MOVE_BY_PLAYER + rounds.get(0).getCurrentPlayer().getName() +
                    ":\n" + e.getMessage());
            cancelToolCardUse();
        }
    }

    public String getActiveTableElement() {
        if (isToolCardActive()) {
            return table.getActiveToolCard().getActiveTableElement();
        } else if (isMoveActive()) {
            return move.getActiveTableElement();
        } else
            return "INACTIVE_TABLE";
    }

    public void skipTurn() {
        if(rounds.get(0).getPlayerTurn(0) != null) {
            rounds.get(0).getPlayerTurn(0).setMovesLeft(0);
            rounds.get(0).getPlayerTurn(0).setToolCardUsed(true);
            table.notifyObservers(getCurrentPlayer() + "'s turn: Turn skipped.");
        }
    }

    public void countScores() {
        for(Player player: table.getPlayers()){
            countScore(player);
            table.getScoreTrack().add(player);
        }
        table.notifyObservers();
    }

    public void countScore(Player player) {
        int score = 0;
        score += player.getPrivateObjectiveCard().countScore(player.getWindowFrame());
        score += player.getNumOfTokens();
        score -= player.getWindowFrame().getEmptyCells();
        for(PublicObjectiveCard pubObjCard : table.getGamePublicObjectiveCards()){
            score += pubObjCard.countScore(player.getWindowFrame());
        }
        if (score>=0)
            player.setScore(score);
        else
            player.setScore(0);
    }


    public String getWinner() {
        return table.getScoreTrack().getWinner().getName();
    }

    public boolean isGameEnded() {
        if (rounds.isEmpty()) {
            table.notifyObservers("Game end.");
            return true;
        } else {
            return false;
        }
    }

    public boolean isRoundEnded() {
        if (rounds.get(0).size()==0) {
            table.getRoundTrack().setRoundDices(table.getDraftPool(), 10-rounds.size());
            rounds.remove(0);
            table.getDraftPool().clear();
            if (!rounds.isEmpty()) {
                drawDices();
                table.notifyObservers();
                table.notifyObservers("New round, draft pool extracted.\nNewTurn.");
            }
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
            rounds.get(0).getCurrentPlayer().getWindowFrame().getPatternCard().disableExceptions();
            rounds.get(0).removeTurn(0);
            if (!rounds.get(0).getPlayerTurns().isEmpty()) {
                table.notifyObservers("New Turn.");
            }
            return true;
        }
        return false;
    }

    public void endGame() {
        if (!isGameEnded()) {
            Player lastPlayer = rounds.get(0).getCurrentPlayer();
            rounds.clear();
            for(Player player: table.getPlayers()){
                if (!player.getName().equals(lastPlayer.getName())) {
                    player.setScore(0);
                    table.getScoreTrack().add(player);
                }
            }
            countScore(lastPlayer);
            table.getScoreTrack().add(0, lastPlayer);
            table.notifyObservers();
        }
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

    public void deleteObserver (Observer o) {
        table.deleteObserver(o);
    }

    public void notifyObservers() {
        table.notifyObservers();
    }

    public void notifyObservers(String message) {
        table.notifyObservers(message);
    }

    public Table getTable() {
        return table;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

}
