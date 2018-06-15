package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;

import java.io.Serializable;
import java.util.ArrayList;

public class ScoreTrack implements Serializable {

    private ArrayList<PlayerTurn> lastRound;
    private ArrayList<Player> scores;

    public ScoreTrack() {
        scores = new ArrayList<>();
        lastRound = new ArrayList<>();
    }

    public void add(Player player) {
        if (scores.isEmpty()) {
            scores.add(player);
            return;
        }

        int i = 0;
        while(i<scores.size() && scores.get(i).getScore() > player.getScore()) {
            i++;
        }
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
    }

    public Player getWinner() {
        return scores.get(0);
    }

    public void setLastRound(ArrayList<PlayerTurn> lastRound) {
        this.lastRound = lastRound;
    }

    public boolean isEmpty() {
        return scores.isEmpty();
    }

    @Override
    public String toString() {
        String scoreTrack = "ScoreTrack:\n";
        for (Player player: scores) {
            scoreTrack = scoreTrack.concat(player.getName() + ": " + player.getScore() + "\n");
        }
        scoreTrack = scoreTrack.concat("\n");
        return scoreTrack;
    }

    public void dump() {
        System.out.println(toString());
    }
}
