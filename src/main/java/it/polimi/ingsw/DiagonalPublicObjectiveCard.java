package it.polimi.ingsw;

public class DiagonalPublicObjectiveCard extends PublicObjectiveCard {

    String restriction;

    public DiagonalPublicObjectiveCard(String name, int ID, int points, String restriction) {
        super(name, ID, points);
        this.restriction = restriction;
    }

    @Override
    public int countScore(WindowFrame windowFrame) {
        int score = 0;

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 5; j++) {
                if(windowFrame.getDice(i, j) != null) {
                    if(i>1 && j>1 && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i-1, j-1).getColor())) {
                        score++;
                    }
                    if(i>1 && j<5 && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i-1, j+1).getColor())) {
                        score++;
                    }
                    if(i<4 && j>1 && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i+1, j-1).getColor())) {
                        score++;
                    }
                    if(i<4 && j<5 && windowFrame.getDice(i, j).getColor().equals(windowFrame.getDice(i+1, j+1).getColor())) {
                        score++;
                    }
                }
            }
        }
        return  score*points;
    }
}
