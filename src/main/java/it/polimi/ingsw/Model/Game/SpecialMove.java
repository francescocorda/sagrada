package it.polimi.ingsw.Model.Game;


import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.exceptions.DiceNotFoundException;
import it.polimi.ingsw.exceptions.InvalidFaceException;

import java.util.ArrayList;
import java.util.Scanner;

public class SpecialMove extends Move {

    public Dice chooseDicefromWindow(WindowFrame windowFrame, String commands){
        int row, col;
        Dice dice = new Dice(Color.ANSI_GREEN);
        try (Scanner in = new Scanner(commands)) {
            windowFrame.dump();
            System.out.println("In quale riga vuoi prendere il dado ?");
            row = in.nextInt();
            System.out.println("In quale colonna vuoi prendere il dado?");
            col = in.nextInt();
        }
        try {
            dice = windowFrame.removeDice(row, col);
        } catch (DiceNotFoundException e) {
            e.printStackTrace();
        }
        return dice;
    }

    public Dice incOrDec(Dice dice, String commands) {  //chiamato in ToolCard1
        String operation;
        try (Scanner in = new Scanner(commands)) {
            operation = new String("-");
            System.out.println("Vuoi aumentare(INC) o diminuire(DEC) il valore del dado?");
            while (!operation.equals("INC") && !operation.equals("DEC") ) {
                operation = in.nextLine();
                if (!operation.equals("INC") && !operation.equals("DEC"))
                    System.out.println("Operazione non valida inserire nuovamente l'operazione.");
            }
        }
        if(operation.equals("INC")){
            try {
                dice.setFace(dice.valueOf()+1);
            } catch (InvalidFaceException e) {
                if(dice.valueOf()==6) {
                    System.out.println("You can't increase a 6");
                }
            }
        }else if(operation.equals("DEC")){
            try {
                dice.setFace(dice.valueOf()-1);
            } catch (InvalidFaceException e) {
                if(dice.valueOf()==1) {
                    System.out.println("You can't decrease a 1");
                }
            }
        }
        return dice;
    }

    public void enableRestriction(WindowFrame windowFrame, String restrictionToIgnore){  //chiamato in TC1, TC3, TC5, TC9
        int i, j;
        for(i=1; i<5; i++){
            for(j=1; j<6; j++){
                if(restrictionToIgnore.compareTo("VALUE")==0){
                    if(windowFrame.getPatternCard().getRestriction(i, j).escape().compareTo(Restriction.ONE.escape())>=0){
                        windowFrame.getPatternCard().setExceptionRestriction(i, j, true);
                    }
                }else if(restrictionToIgnore.compareTo("COLOR")==0){
                    if(windowFrame.getPatternCard().getRestriction(i, j).escape().compareTo(Restriction.ONE.escape())<0){
                        windowFrame.getPatternCard().setExceptionRestriction(i, j, true);
                    }
                }else if(restrictionToIgnore.compareTo("POSITION")==0){
                    windowFrame.getPatternCard().setExceptionPosition(i, j, true);
                }
            }
        }
    }

    public Dice exchangeDiceRT(ArrayList<ArrayList<Dice>> roundTrack, Dice dice, String commands){
        int i, j;
        int round, index;
        Dice temp;
        try (Scanner in = new Scanner(commands)) {
            for (i = 0; i < roundTrack.size(); i++) {
                System.out.println("Round " + (i + 1) + ":");
                for (j = 0; j < roundTrack.get(i).size(); j++) {
                    System.out.print((j + 1) + "" + roundTrack.get(i).get(j).toString());
                }
                System.out.println();
            }
            System.out.println("In quale round vuoi scambiare il dado?");
            round = in.nextInt() - 1;
            System.out.println("Col quale dado vuoi scambiare " + dice.toString() + " nel round " + (round + 1) + "?");
            index = in.nextInt() - 1;
        }
        temp = roundTrack.get(round).remove(index);
        roundTrack.get(round).add(index, dice);
        for(i=0; i<roundTrack.size(); i++){
            System.out.println("Round "+(i+1)+":");
            for(j=0; j<roundTrack.get(i).size(); j++){
                System.out.print((j+1)+""+roundTrack.get(i).get(j).toString());
            }
            System.out.println();
        }
        return temp;
    }



    public void setFace(Dice dice, String commmands){
        int val;
        try (Scanner in = new Scanner(commmands)) {
            System.out.println("Quale valore vuoi assegnare al dado? ");
            val = in.nextInt();
            while (val < 1 || val > 6) {
                System.out.println("Valore non valido, inserire un altro valore.");
                val = in.nextInt();
            }
        }
        try {
            dice.setFace(val);
        } catch (InvalidFaceException e) {
            e.printStackTrace();
        }
    }
}
