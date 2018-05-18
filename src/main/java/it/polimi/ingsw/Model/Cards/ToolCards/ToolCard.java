package it.polimi.ingsw.Model.Cards.ToolCards;



import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public abstract class ToolCard {
    protected int ID;
    protected String name;
    protected String description;
    protected int numOfTokens;


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

    public abstract Dice useAbility(ArrayList<Dice> draftPool, RoundTrack roundTrack, DiceBag diceBag, Player player, ArrayList<PlayerTurn> playerTurns, ArrayList<String> commands) throws DiceNotFoundException, InvalidFaceException, WrongRoundException, InvalidNeighboursException, MismatchedRestrictionException, OccupiedCellException, InvalidFirstMoveException;
}
