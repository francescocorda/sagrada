package it.polimi.ingsw.model.game;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreTrack implements Serializable {

    private transient ArrayList<PlayerTurn> lastRound;
    private ArrayList<Player> scores;

    /**
     * creates a new {@link ScoreTrack}.
     */
    public ScoreTrack() {
        scores = new ArrayList<>();
        lastRound = new ArrayList<>();
    }

    /**
     * adds the given {@link Player} player at the given int index of {@link #scores}.
     * @param index : the given int index
     * @param player : the given {@link Player} player
     */
    public void add(int index, Player player) {
        scores.add(index, player);
    }

    /**
     * adds the given {@link Player} player to {@link #scores}.
     * @param player : the given {@link Player} player
     */
    public void add(Player player) {
        if (scores.isEmpty()) {
            scores.add(player);
            return;
        }

        int i = 0;
        while(i<scores.size() && scores.get(i).getScore() > player.getScore()) {
            i++;
        }
        if(i<scores.size()){
            if (scores.get(i).getScore() == player.getScore()) {
                int score1 = scores.get(i).getPrivateObjectiveCard().countScore(scores.get(i).getWindowFrame());
                int score2 = player.getPrivateObjectiveCard().countScore(player.getWindowFrame());
                if (score1 == score2) {
                    int tokens1 = scores.get(i).getNumOfTokens();
                    int tokens2 = player.getNumOfTokens();
                    if (tokens1 == tokens2) {
                        int j = lastRound.size()-1;
                        while(j>0 && !(lastRound.get(j).getPlayer().getName().equals(scores.get(i).getName()))
                                && !(lastRound.get(j).getPlayer().getName().equals(player.getName()))) {
                            j--;
                        }
                        if (lastRound.get(j).getPlayer().getName().equals(player.getName())) {
                            scores.add(i, player);
                        } else {
                            scores.add(i+1, player);
                        }
                    } else if (tokens1 > tokens2) {
                        scores.add(i+1, player);
                    } else {
                        scores.add(i, player);
                    }
                } else if(score1 > score2) {
                    scores.add(i+1, player);
                } else {
                    scores.add(i, player);
                }
            } else {
                scores.add(i, player);
            }
        }else {
            scores.add(player);
        }

    }

    /**
     * @return the {@link Player} that's the winner of the game.
     */
    public Player getWinner() {
        return scores.get(0);
    }

    /**
     * sets {@link #lastRound} as the given {@link ArrayList<PlayerTurn>} lastRound.
     * @param lastRound : the given {@link ArrayList<PlayerTurn>} lastRound
     */
    public void setLastRound(ArrayList<PlayerTurn> lastRound) {
        this.lastRound = lastRound;
    }

    /**
     * @return whether {@link #scores} is empty or not.
     */
    public boolean isEmpty() {
        return scores.isEmpty();
    }

    /**
     * @return the {@link String} representation of this {@link ScoreTrack}.
     */
    @Override
    public String toString() {
        String scoreTrack = "ScoreTrack:\n";
        for (Player player: scores) {
            scoreTrack = scoreTrack.concat(player.getName() + ": " + player.getScore() + "\n");
        }
        scoreTrack = scoreTrack.concat("\n");
        return scoreTrack;
    }

    /**
     * displays {@link #toString()}.
     */
    public void dump() {
        System.out.println(toString());
    }

    /**
     * @return a new {@link ArrayList<Player>} given {@link #scores}.
     */
    public ArrayList<Player> getScores(){
        return new ArrayList<>(scores);
    }
}
