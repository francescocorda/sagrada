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
        windowFrame = new WindowFrame();
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
