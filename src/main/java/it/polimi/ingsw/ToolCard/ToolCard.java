package it.polimi.ingsw.ToolCard;



import it.polimi.ingsw.Dice;
import it.polimi.ingsw.DiceBag;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.PlayerTurn;
import it.polimi.ingsw.exceptions.WrongRoundException;

import java.util.ArrayList;

public abstract class ToolCard {
    protected int ID;
    protected String name;
    protected String description;
    protected int numOfTokens;
    protected SpecialMove specialMove = new SpecialMove();


    public int getID(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public int getNumOfTokens(){
        return this.numOfTokens;
    }

    public void increaseNumOfTokens(int tokens){
        numOfTokens += tokens;
    }

    public void dump(){
        System.out.println("ID: "+ID);
        System.out.println("Name: "+name);
        System.out.println("Description: "+description);
        System.out.println("Number of tokens: "+numOfTokens);
    }

    public abstract void useAbility(ArrayList<Dice> drawPool, ArrayList<ArrayList<Dice>> roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, String commands) throws WrongRoundException;
}
