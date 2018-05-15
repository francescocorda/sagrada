package it.polimi.ingsw.Model.Cards.ToolCards;



import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.DiceBag;
import it.polimi.ingsw.Model.Game.SpecialMove;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.PlayerTurn;
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
