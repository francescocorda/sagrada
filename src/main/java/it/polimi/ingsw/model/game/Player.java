package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int numOfTokens;
    private int score;
    private PrivateObjectiveCard privateObjectiveCard;
    private WindowFrame windowFrame;

    /**
     * creates a new {@link Player} from the given {@link String} name.
     * @param name : the given {@link String} name
     */
    public Player(String name) {
        this.name = name;
        numOfTokens = 0;
        score = 0;
        privateObjectiveCard = null;
        windowFrame = new WindowFrame();
    }

    /**
     * sets {@link #name} as the given {@link String} name.
     * @param name : the given {@link String} name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * sets {@link #numOfTokens} as the value of the given int numOfTokens.
     * @param numOfTokens : the given int numOfTokens
     * @throws NotValidInputException if the given int numOfTokens is not valid
     */
    public void setNumOfTokens(int numOfTokens) throws NotValidInputException {
        if(numOfTokens>=0) {
            this.numOfTokens = numOfTokens;
        } else {
            throw new NotValidInputException();
        }
    }

    /**
     * @return {@link #numOfTokens}.
     */
    public int getNumOfTokens() {
        return numOfTokens;
    }

    /**
     * sets {@link #windowFrame} as the given {@link WindowFrame} windowFrame.
     * @param windowFrame : the given {@link WindowFrame} windowFrame
     */
    public void setWindowFrame(WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
    }

    /**
     * @return {@link #windowFrame}.
     */
    public WindowFrame getWindowFrame() {
        return windowFrame;
    }

    /**
     * sets {@link #score} as the given int score.
     * @param score : the given int score
     */
    public void setScore(int score){
        this.score=score;
    }

    /**
     * @return {@link #score}.
     */
    public int getScore(){
        return score;
    }

    /**
     * sets player's {@link WindowFrame#patternCard} as the given {@link WindowFrame} windowFrame.
     * @param patternCard : the given {@link WindowFrame} windowFrame
     */
    public void setPatternCard(PatternCard patternCard){
        this.windowFrame.setPatternCard(patternCard);
    }

    /**
     * @return player's {@link WindowFrame#patternCard}.
     */
    public PatternCard getPatternCard(){
        return this.windowFrame.getPatternCard();
    }

    /**
     * sets {@link #privateObjectiveCard} as the given {@link PrivateObjectiveCard} privateObjectiveCard.
     * @param privateObjectiveCard : the given {@link PrivateObjectiveCard} privateObjectiveCard
     */
    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        this.privateObjectiveCard = privateObjectiveCard;
    }

    /**
     * @return {@link #privateObjectiveCard}.
     */
    public PrivateObjectiveCard getPrivateObjectiveCard() {
        return privateObjectiveCard;
    }

    /**
     * @return the {@link String} representation of this {@link Player}.
     */
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

    /**
     * displays {@link #toString()}.
     */
    public void dump(){
        System.out.println(toString());
    }

}
