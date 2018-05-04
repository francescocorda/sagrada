package it.polimi.ingsw;

public class PrivateObjectiveCard extends ObjectiveCard {

    Color color;

    public PrivateObjectiveCard(String name, int ID, Color color) {
        super(name, ID);
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int countScore(WindowFrame windowFrame) {

        int score = 0;

        for (int i=1; i<=4; i++) {
            for (int j=1; j<=5; j++) {
                if (windowFrame.getDice(i,j) != null && windowFrame.getDice(i,j).getColor().equals(color)) {
                    score += windowFrame.getDice(i,j).valueOf();
                }
            }

        }
        return score;
    }

    public String toString() {
        return " ID: " + getID() + "\n Name: " + color.escape()+ getName() + Color.RESET + "\n Description: " + getDescription()+"\n";
    }

    public void dump() {
        System.out.println(toString());
    }

}
