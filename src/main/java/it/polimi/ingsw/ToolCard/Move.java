package it.polimi.ingsw.ToolCard;



import it.polimi.ingsw.Dice;
import it.polimi.ingsw.WindowFrame;
import it.polimi.ingsw.exceptions.InvalidFirstMoveException;
import it.polimi.ingsw.exceptions.InvalidNeighboursException;
import it.polimi.ingsw.exceptions.MismatchedRestrictionException;
import it.polimi.ingsw.exceptions.OccupiedCellException;

import java.util.ArrayList;
import java.util.Scanner;

public class Move {

    public Dice chooseDiceFromDP(ArrayList<Dice> drawPool, String commands){  //chiamato in TC1, TC5, TC9
        int index;
        try (Scanner in = new Scanner(commands)) {
            index = 0;
            int n = 1;
            System.out.println("Quale dado vuoi prendere?");
            for (Dice d : drawPool) {
                System.out.println("Dado " + n + " :" + d);
                n++;
            }
            while (1 > index || index > drawPool.size()) {
                index = in.nextInt();
                if (index < 1 || index > drawPool.size())
                    System.out.println("Inserimento non valido, inserisci un altro dado.");
            }
        }

        return drawPool.remove(index-1);
    }

    public void ordinaryMove(WindowFrame windowFrame, Dice dice, ArrayList<Dice> drawPool, String commands) throws InvalidNeighboursException, OccupiedCellException, MismatchedRestrictionException, InvalidFirstMoveException {
        int row, col;
        try (Scanner in = new Scanner(commands)) {
            windowFrame.dump();
            System.out.println("In quale riga vuoi inserire il dado " + dice.toString() + " ?");
            row = in.nextInt();
            System.out.println("In quale colonna vuoi inserire il dado?");
            col = in.nextInt();
        }

        windowFrame.setDice(row, col, dice);

        windowFrame.dump();
    }
}
