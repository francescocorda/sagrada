package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveDeck;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveDeck;
import it.polimi.ingsw.exceptions.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import static it.polimi.ingsw.Model.Game.RoundTrack.NUMBER_OF_ROUND;

public class Game extends Observable implements Serializable {
    int matchID;
    ArrayList<Player> players;
    PrivateObjectiveDeck privateObjectiveDeck;
    PublicObjectiveDeck publicObjectiveDeck;
    PatternDeck patternDeck;
    ArrayList<PublicObjectiveCard> matchPublicObjectiveDeck;
    ArrayList<PatternCard> matchPatternDeck;
    //ToolCardDeck toolCardDeck;
    ArrayList<Round> rounds;
    private Table table;
    private static final String INVALID_MOVE_BY_PLAYER = "Invalid move by player: ";

    public Game(int matchID, ArrayList<String> names ){
        this.matchID = matchID;
        this.players = new ArrayList<>();
        for(int i=0; i<names.size(); i++){
            this.players.add(new Player(names.get(i)));
        }
        privateObjectiveDeck = new PrivateObjectiveDeck();
        patternDeck = new PatternDeck();
        publicObjectiveDeck = new PublicObjectiveDeck();
        matchPublicObjectiveDeck = new ArrayList<>();
        matchPatternDeck = new ArrayList<>();
        rounds = new ArrayList<>();
        //toolCardDeck = new ToolCardDeck();

        //prove senza sfasciare tutto
        this.table = new Table();
        table.setDiceBag(new DiceBag());
        table.setRoundTrack(new RoundTrack());
        table.setPlayers(players);
        for (int i = 0; i < NUMBER_OF_ROUND; i++) {
            rounds.add(new Round(this.players,i%players.size(), table));
        }
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

    public ArrayList<PatternCard> drawPatternCards() {
        ArrayList<PatternCard> patterns = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 2; i++) {
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
        if(patternIndex != 0 && patternIndex != 1){
            throw new NotValidInputException();
        }
        while(!players.get(i).getName().equals(player)&& i<players.size()){
            i++;
        }
        if(i==players.size()){
            throw new NotValidInputException();
        } else {
            if(players.get(i).getPatternCard() == null) {
                players.get(i).setPatternCard(matchPatternDeck.get(2*i + patternIndex));
                players.get(i).setNumOfTokens(matchPatternDeck.get(2*i + patternIndex).getDifficulty());
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
        setChanged();
        notifyObservers(table);
        notifyObservers("Play Game.");
        return true;
    }

    public String getCurrentPlayer() {
        return rounds.get(0).getPlayerTurn(0).getPlayer().getName();
    }

    public boolean isCurrentPlayer(String name) {
        if(rounds.get(0).getPlayerTurn(0).getPlayer().getName().equals(name)) {
            return true;
        } else
        return false;
    }

    public boolean performMove(ArrayList<String> commands) {
        boolean moveDone = false;
        int indexDP = Integer.parseInt(commands.get(0));
        Dice dice = table.getDraftPool().get(indexDP-1);
        try {
            if(rounds.get(0).getPlayerTurn(0).size() != 0) {
                rounds.get(0).getPlayerTurn(0).getMove(0).performMove(commands);
                moveDone = true;
                notifyObservers(table);
            }
        } catch (DiceNotFoundException e) {
            table.getDraftPool().add(indexDP-1, dice);
            notifyObservers(table);
            notifyObservers(INVALID_MOVE_BY_PLAYER + getCurrentPlayer() + ":\n" +
                    "Dice not found.");
        } catch (InvalidFaceException e) {
            table.getDraftPool().add(indexDP-1, dice);
            notifyObservers(table);
            notifyObservers(INVALID_MOVE_BY_PLAYER + getCurrentPlayer() + ":\n" +
                    "Value not compatible with any dice's face.");
        } catch (MismatchedRestrictionException e) {
            table.getDraftPool().add(indexDP-1, dice);
            notifyObservers(table);
            notifyObservers(INVALID_MOVE_BY_PLAYER + getCurrentPlayer() + ":\n" +
                    "The dice doesn't match the pattern restriction.");
        } catch (InvalidNeighboursException e) {
            table.getDraftPool().add(indexDP-1, dice);
            notifyObservers(table);
            notifyObservers(INVALID_MOVE_BY_PLAYER + getCurrentPlayer() + ":\n" +
                    "The position isn't near any valid neighbour.");
        } catch (OccupiedCellException e) {
            table.getDraftPool().add(indexDP-1, dice);
            notifyObservers(table);
            notifyObservers(INVALID_MOVE_BY_PLAYER + getCurrentPlayer() + ":\n" +
                    "Position already occupied.");
        } catch (InvalidFirstMoveException e) {
            table.getDraftPool().add(indexDP-1, dice);
            notifyObservers(table);
            notifyObservers(INVALID_MOVE_BY_PLAYER + getCurrentPlayer() + ":\n" +
                    "Each player’s first dice of the game must be placed on an edge or corner space,\n" +
                    "respecting all the other restrictions");
        } catch (IndexOutOfBoundsException e) {
            table.getDraftPool().add(indexDP-1, dice);
            notifyObservers(table);
            notifyObservers(INVALID_MOVE_BY_PLAYER + getCurrentPlayer() + ":\n" +
                    "Invalid window coordinates.");
        } catch (WrongRoundException e) {
            e.printStackTrace();
        }
        return moveDone;
    }


    public void skipTurn() {
        if(rounds.get(0).getPlayerTurn(0) != null) {
            rounds.get(0).removeTurn(0);
        }

    }

    public ArrayList<Integer> countScores(){
        ArrayList<Integer> scores = new ArrayList<>();
        int score;
        for(Player player: players){
            score = 0;
            score += player.getPrivateObjectiveCard().countScore(player.getWindowFrame());
            score += player.getNumOfTokens();

            for(PublicObjectiveCard pubObjCard : matchPublicObjectiveDeck){
                score += pubObjCard.countScore(player.getWindowFrame());
            }
            player.setScore(score);
            scores.add(score);
        }

        return scores;
    }

    public boolean isGameEnded() {
        if (rounds.isEmpty()) {
            notifyObservers("Game end.");
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
            notifyObservers(table);
            notifyObservers("New round, draftpool extracted.\nNewTurn.");
            return true;
        }
        return false;
    }
    public boolean isTurnEnded() {
        if (rounds.get(0).getPlayerTurn(0).size()==0) {
            rounds.get(0).removeTurn(0);
            notifyObservers("New Turn.");
            return true;
        }
        return false;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
