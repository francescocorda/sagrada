package it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.Serializable;

public class Player implements Serializable {
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
        windowFrame = new WindowFrame();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNumOfTokens(int numOfTokens) throws NotValidInputException {
        if(numOfTokens>=0) {
            this.numOfTokens = numOfTokens;
        } else {
            throw new NotValidInputException();
        }
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

    public void setScore(int score){
        this.score=score;
    }

    public int getScore(){
        return score;
    }

    public void setPatternCard(PatternCard patternCard){
        this.windowFrame.setPatternCard(patternCard);
    }

    public PatternCard getPatternCard(){
        return this.windowFrame.getPatternCard();
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    @Override
    public String toString(){
        String string="";
        string=string.concat("Name: "+name+"\n"+
                "Number of tokens: "+ numOfTokens+"\n"+
                "Score: "+score+"\n"+
                "\nPrivate Objective Card:\n"+
                (privateObjectiveCard == null ? "NOT ADDED YET\n" : privateObjectiveCard.toString()+"\n")+
                "WindowFrame:\n"+
                (windowFrame==null ? "NOT ADDED YET\n" : windowFrame.toString()));
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }

}
