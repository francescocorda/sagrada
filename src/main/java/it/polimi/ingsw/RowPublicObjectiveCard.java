package it.polimi.ingsw;



import java.util.HashSet;

public class RowPublicObjectiveCard extends PublicObjectiveCard {

    int points;
    String restriction;

    public RowPublicObjectiveCard(String name, int ID, int points, String restriction) {
        super(name, ID, points);
        this.points = points;
        this.restriction = restriction;
    }


    @Override
    public int countScore(WindowFrame windowFrame) {
        int score = 0;

        for (int i = 1; i <= 4; i++) {
            HashSet<String> set = new HashSet<String>();
            for (int j = 1; j <= 5; j++) {
                if(windowFrame.getDice(i, j) != null) {
                    if (restriction.equals("color")) {
                        set.add(windowFrame.getDice(i, j).getColor().escape());
                    } else if(restriction.equals("face")) {
                        set.add(windowFrame.getDice(i, j).getFace());
                    }
                }
            }
            if (set.size()==5) {
                score += points;
            }
        }
        return score;
    }

}
