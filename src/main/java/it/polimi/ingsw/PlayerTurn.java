package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.*;

import java.util.ArrayList;

public class PlayerTurn {
    private ArrayList<Dice> drawPool;
    private Player player;
    private ActionPerformed actionPerformed;
    //View view;

    public PlayerTurn(Player player, ArrayList<Dice> drawPool) {
        this.drawPool=drawPool;
        this.player = player;
        this.actionPerformed = ActionPerformed.NOTHING;
    }

    public Player getPlayer() {
        return player;
    }

    public void setActionPerformed(ActionPerformed actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    public ActionPerformed getActionPerformed() {
        return actionPerformed;
    }

    public void move() {

        //Observer mossa normale
        int index=1;
        int row=1;
        int col=1;
        try {
            player.getWindowFrame().setDice(row, col, drawPool.remove(index));
        } catch (MismatchedRestrictionException e) {
            e.printStackTrace();
        } catch (InvalidNeighboursException e) {
            e.printStackTrace();
        } catch (InvalidFirstMoveException e) {
            e.printStackTrace();
        } catch (OccupiedCellException e) {
            e.printStackTrace();
        }

        //Observer mossa toolcard

        //Observer salta turno

        //Observer timer 90 secondi

    }

    public void payTokens(int toPay) throws  NotValidInputException{
        player.setNumOfTokens(player.getNumOfTokens()-toPay);
    }

    public void useToolCard() {

    }

    @Override
    public String toString(){
        String string="DrawPool:\n";
        if(drawPool==null)
            string=string.concat("NOT ADDED YET");
        else
            for(Dice dice: drawPool)
                string=string.concat(dice.toString());
        string=string.concat("\nPlayer:\n"+player.toString());
        string=string.concat("Action Performed: "+actionPerformed);
        return string;
    }

    public void dump(){
        System.out.println(toString());
    }


}