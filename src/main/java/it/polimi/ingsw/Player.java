package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;

public class Player {
    private String name;
    private int numOfTokens;
    private int score;
    private PrivateObjectiveCard privateObjectiveCard;
    private WindowFrame windowFrame;

    public Player(String name) {
        this.name = name;
        numOfTokens = 0;
        score = 0;
        privateObjectiveCard = null;
        windowFrame = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumOfTokens(int numOfTokens) throws NotValidInputException{

        if(numOfTokens<0) {                                                             //forse inutile
            throw new NotValidInputException();
        }
        this.numOfTokens = numOfTokens;
    }

    public int getNumOfTokens() {
        return numOfTokens;
    }

    public void setWindowFrame(WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
    }

    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }



}
