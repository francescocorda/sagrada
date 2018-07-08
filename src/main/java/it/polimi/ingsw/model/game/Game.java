package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.patterns.PatternDeck;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveDeck;
import it.polimi.ingsw.model.cards.public_objectives.PublicObjectiveCard;
import it.polimi.ingsw.model.cards.public_objectives.PublicObjectiveDeck;
import it.polimi.ingsw.model.cards.toolcard.ToolCard;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static it.polimi.ingsw.model.game.RoundTrack.NUMBER_OF_ROUNDS;

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
    public static final String PLAY_GAME = "Play game.";
    public static final int PUB_OBJ_CARDS_DIMENSION = 3;
    public static final int TOOL_CARDS_DIMENSION = 3;
    public static final int PROPOSED_PATTERNS = 4;
    private ArrayList<Dice> old_draftPool;
    private WindowFrame old_windowFrame;
    private DiceBag old_diceBag;
    private RoundTrack old_roundTrack;
    private ArrayList<PlayerTurn> old_playerTurns;

    /**
     * creates a new {@link Game} given the parameters, the {@link Table} and the {@link ArrayList<Round>} are
     * instantiated
     * @param matchID the given match ID.
     * @param names the given {@link ArrayList<String>} of players username.
     */
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

    /**
     * @return an {@link ArrayList<String>} of the players user names
     */
    public ArrayList<String> getUserNames() {
        ArrayList<String> userNames = new ArrayList<>();
        for (Player player: players) {
            userNames.add(player.getName());
        }
        return userNames;
    }

    /**
     * @return an {@link ArrayList<ToolCard>} of the tool cards
     */
    public ArrayList<ToolCard> getToolCards() {
        return toolCards;
    }

    /**
     * Sets the tool card deck used in the game
     * @param toolCards is the given {@link ArrayList<ToolCard>}
     */
    public void setToolCards(ArrayList<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    /**
     * Sets the pattern deck used in the game
     * @param deck is the given {@link PatternDeck}
     */
    public void setPatternDeck(PatternDeck deck){
        patternDeck = new PatternDeck(deck);
    }

    /**
     * Sets the public objective deck used in the game
     * @param deck is the given {@link ArrayList<PublicObjectiveCard>}
     */
    public void setPublicObjectiveDeck(ArrayList<PublicObjectiveCard> deck){
        publicObjectiveDeck = new PublicObjectiveDeck(deck);
    }

    /**
     * Draws the a number of dices from the {@link DiceBag} to the Draw Pool according to the game rule:
     * number of dices extracted every round = (2 * n. of players) + 1
     */
    public void drawDices() {
        table.setDrawPool(2*players.size()+1);
    }

    /**
     * Assigns randomly a {@link PrivateObjectiveCard} to the player given its name.
     * @param player is the given player's name
     * @return the assigned {@link PrivateObjectiveCard}
     * @throws NotValidInputException if the player's name received as a parameters is not contained in the game
     * players list
     */
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

    /**
     * Draw the {@link PublicObjectiveCard}s to use in the game
     */
    public void drawPublicObjectiveCards() {
        Random rand = new Random();
        for (int i = 0; i < PUB_OBJ_CARDS_DIMENSION; i++) {
            int index = rand.nextInt(publicObjectiveDeck.size());
            table.getGamePublicObjectiveCards().add(publicObjectiveDeck.removePuOC(index));
        }
    }

    /**
     * Draw the {@link ToolCard}s to use in the game
     */
    public void drawToolCards() {
        Random rand = new Random();
        for (int i = 0; i < TOOL_CARDS_DIMENSION; i++) {
            int index = rand.nextInt(toolCards.size());
            table.getGameToolCards().add(toolCards.remove(index));
        }
    }

    /**
     * Draw the {@link PatternCard}s to use in the game
     */
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

    /**
     * Assigns a {@link PatternCard} to the player given its name and the selected card index (from 0 to 3).
     * @param player is the given player's name
     * @param patternIndex is the selected card index (from 0 to 3)
     * @return true if the pattern card is assigned to the player, false otherwise
     */
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

    /**
     * @return true if all players pattern cards have been assigned
     */
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

    /**
     * @return the name of the player that's playing the turn at the moment of the call
     */
    public String getCurrentPlayer() {
        if (!isGameEnded())
            return rounds.get(0).getCurrentPlayer().getName();
        else
            return null;
    }

    /**
     * @return true if the given {@link String} name is equal to the one of the player that's playing
     * the turn at the moment of the call
     */
    public boolean isCurrentPlayer(String name) {
        if (!isGameEnded())
            return rounds.get(0).getPlayerTurn(0).getPlayer().getName().equals(name);
        else
            return false;
    }

    /**
     * Perform a move given a list of commands
     * @param commands is the given {@link ArrayList<String>} of commands
     */
    public void performMove(ArrayList<String> commands) {
        if(rounds.get(0).getPlayerTurn(0).getMovesLeft() > 0) {
            try {
                move.performMove(commands);
            } catch (ImpossibleMoveException e) {
                cancelMove();
            }
        }
    }

    /**
     * Generates a new move object.
     */
    public void createMove() {
        move = new Move(table, rounds.get(0));
        saveGame();
        rounds.get(0).getPlayerTurn(0).setMoveActive(true);
        move.explainEffect(table, rounds.get(0));
    }


    /**
     * @return true if the player is allowed to perform a move in the moment of the call
     */
    public boolean moveAllowed() {
        if (rounds.get(0).getPlayerTurn(0).getMovesLeft()>0) {
            table.notifyObservers(getCurrentPlayer() + "'s turn: move allowed.");
            return true;
        }
        table.notifyObservers(getCurrentPlayer() + "'s turn: move not allowed.");
        return false;
    }

    /**
     * Saves an instance of the current state of the game
     */
    private void saveGame() {
        this.old_draftPool = table.cloneDrawPool();
        this.old_windowFrame = new WindowFrame(rounds.get(0).getCurrentPlayer().getWindowFrame());
        this.old_diceBag = new DiceBag(table.getDiceBag());
        this.old_roundTrack = new RoundTrack(table.getRoundTrack());
        this.old_playerTurns = new ArrayList<>(rounds.get(0).getPlayerTurns());
    }

    /**
     * Cancel the tool card actions rolling back the state of the game at the moment before the use.
     * Refunds the player that used the tool card giving back the tokens spent.
     */
    public void cancelToolCardUse() {
        if (rounds.get(0).getPlayerTurn(0).isToolCardActive()) {
            table.setDrawPool(old_draftPool);
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

    /**
     * Cancel the move actions rolling back the state of the game at the moment before the use.
     */
    public void cancelMove() {
        if(rounds.get(0).getPlayerTurn(0).isMoveActive()) {
            rounds.get(0).getPlayerTurn(0).setMoveActive(false);
            table.setDrawPool(old_draftPool);
            table.setActiveDice(null);
            rounds.get(0).getCurrentPlayer().setWindowFrame(old_windowFrame);
            table.notifyObservers();
        }
    }

    /**
     * @return true if the player is allowed to use a tool card in the moment of the call
     */
    public boolean toolCardUseAllowed(int indexTC) {
        if(table.getGameToolCards().get(indexTC).useAllowed(rounds.get(0).getPlayerTurn(0))) {
            table.notifyObservers(getCurrentPlayer() + "'s turn: tool card " + table.getGameToolCards().get(indexTC).getName() + " can be used.");
            return true;
        }
        table.notifyObservers(getCurrentPlayer() + "'s turn: tool card " + table.getGameToolCards().get(indexTC).getName() + " can't be used in this moment.");
        return false;
    }

    /**
     * Checks that the player has a sufficient number of tokes to buy the tool card and complete the transaction
     * @param indexTC is the index of the chosen tool card (from 0 to 3)
     * @return true if the purchase of the tool card was accepted
     */
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

    /**
     * @return the number of input parameters that the tool card's effect needs to be used.
     */
    public int getToolCardCommandsSize() {
        return table.getActiveToolCard().getCommandsLength();
    }

    /**
     * @return the number of input parameters that the move's effect needs to be used.
     */
    public int getMoveCommandsSize() {
        return move.getCommandsLength();
    }

    /**
     * use the tool card ability to perform a special move
     * @param commands is the {@link ArrayList<String>} of commands needed by the tool card to operate
     */
    public void useToolCard(ArrayList<String> commands) {
        try {
            table.getActiveToolCard().useToolCard(commands, table, rounds.get(0));
        } catch (ImpossibleMoveException e) {
            table.notifyObservers(INVALID_MOVE_BY_PLAYER + rounds.get(0).getCurrentPlayer().getName() +
                    ":\n" + e.getMessage());
            cancelToolCardUse();
        }
    }

    /**
     * @return the actual active element of the table
     */
    public String getActiveTableElement() {
        if (isToolCardActive()) {
            return table.getActiveToolCard().getActiveTableElement();
        } else if (isMoveActive()) {
            return move.getActiveTableElement();
        } else
            return "INACTIVE_TABLE";
    }

    /**
     * skips the actual turn
     */
    public void skipTurn() {
        if(rounds.get(0).getPlayerTurn(0) != null) {
            rounds.get(0).getPlayerTurn(0).setMovesLeft(0);
            rounds.get(0).getPlayerTurn(0).setToolCardUsed(true);
            table.notifyObservers(getCurrentPlayer() + "'s turn: Turn skipped.");
        }
    }

    /**
     * the method counts the scores and adds them to the {@link ScoreTrack}, then it notifies
     * the observers about the final rank
     */
    public void countScores() {
        for(Player player: table.getPlayers()){
            countScore(player);
            table.getScoreTrack().add(player);
        }
        table.notifyObservers();
        table.notifyObservers("Game end.");
    }

    /**
     * counts the score of one player
     * @param player is the given {@link Player}
     */
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


    /**
     * @return the winner of the game
     */
    public String getWinner() {
        return table.getScoreTrack().getWinner().getName();
    }

    /**
     * @return true if the game is ended
     */
    public boolean isGameEnded() {
        if (rounds.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return true if the current round is ended
     */
    public boolean isRoundEnded() {
        if (rounds.get(0).size()==0) {
            table.getRoundTrack().setRoundDices(table.getDrawPool(), 10-rounds.size());
            rounds.remove(0);
            table.getDrawPool().clear();
            if (!rounds.isEmpty()) {
                drawDices();
                table.notifyObservers();
                table.notifyObservers("New round, draft pool extracted.\nNewTurn.");
            }
            return true;
        }
        return false;
    }

    /**
     * @return true if the current turn is ended
     */
    public boolean isTurnEnded() {
        if (rounds.get(0).getPlayerTurn(0).isEnded()) {
            if(table.getActiveToolCard() != null) {
                table.getActiveToolCard().resetToolCard(table, rounds.get(0));
                table.removeActiveToolCard();
            }
            if(table.getActiveDice() != null) {
                table.getDrawPool().add(table.getActiveDice());
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

    /**
     * ends the game counting the final scores and notifying the players with the rank
     */
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
            table.notifyObservers("Game end.");
        }
    }


    /**
     * @return true if the move is active in the current turn at the moment of the call
     */
    public boolean isMoveActive() {
        PlayerTurn playerTurn = rounds.get(0).getPlayerTurn(0);
        if (playerTurn.isMoveActive()) {
            return true;
        }
        return false;
    }

    /**
     * @return true if a tool card is active in the current turn at the moment of the call
     */
    public boolean isToolCardActive() {
        PlayerTurn playerTurn = rounds.get(0).getPlayerTurn(0);
        return playerTurn.isToolCardActive();
    }

    /**
     *
     * @return true if a tool card has already been used during the current turn
     */
    public boolean isToolCardUsed() {
        return rounds.get(0).getPlayerTurn(0).isToolCardUsed();
    }

    /**
     * adds a given {@link Observer} o to the {@link Table}
     * @param o : the given {@link Observer}
     */
    public void addObserver (Observer o) {
        table.addObserver(o);
    }

    /**
     * deletes a given {@link Observer} o from the {@link Table}
     * @param o : the given {@link Observer}
     */
    public void deleteObserver (Observer o) {
        table.deleteObserver(o);
    }

    /**
     * deletes all {@link Observer} from the {@link Table}
     */
    public void deleteObservers() {
        table.deleteObservers();
    }

    /**
     * notifies all {@link Observer} of the {@link Table} about it's current state.
     */
    public void notifyObservers() {
        table.notifyObservers();
    }

    /**
     * notifies all {@link Observer} of the {@link Table} about a given {@link String} message.
     * @param message : the given {@link String} message
     */
    public void notifyObservers(String message) {
        table.notifyObservers(message);
    }

    /**
     * @return an instance of the {@link Table}
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return a deep copy of the {@link Table}
     */
    public Table getTableCopy() {
        return table.copy();
    }

    /**
     * @return an {@link ArrayList} of the game rounds
     */
    public ArrayList<Round> getRounds() {
        return rounds;
    }

}
