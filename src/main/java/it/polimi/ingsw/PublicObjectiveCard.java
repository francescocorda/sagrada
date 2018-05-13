package it.polimi.ingsw;

public abstract class PublicObjectiveCard extends ObjectiveCard {

    int points;

    public PublicObjectiveCard(String name, int ID, int points) {
        super(name, ID);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }


    @Override
    public String toString() {
        String string = super.toString();
        string=string.concat("\nPoints: " + points);
        return string;
    }

    @Override
    public void dump() {
        System.out.println(toString());
    }

}
