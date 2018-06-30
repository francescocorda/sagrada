package it.polimi.ingsw.Model.Cards.PrivateObjectives;

import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.Serializable;
import java.util.ArrayList;

public class PrivateObjectiveDeck implements Serializable {
    private ArrayList<PrivateObjectiveCard> privateObjectiveDeck;

    private PrivateObjectiveCard red;
    private PrivateObjectiveCard green;
    private PrivateObjectiveCard yellow;
    private PrivateObjectiveCard blue;
    private PrivateObjectiveCard purple;

    public PrivateObjectiveDeck() {
        privateObjectiveDeck = new ArrayList<>();

        red = new PrivateObjectiveCard("Sfumature Rosse", 1, Color.RED);
        red.setDescription("Somma dei valori su tutti i dadi rossi");
        green = new PrivateObjectiveCard("Sfumature Verdi", 2, Color.GREEN);
        green.setDescription("Somma dei valori su tutti i dadi verdi");
        yellow = new PrivateObjectiveCard("Sfumature Gialle", 3, Color.YELLOW);
        yellow.setDescription("Somma dei valori su tutti i dadi gialli");
        blue = new PrivateObjectiveCard("Sfumature Blu", 4, Color.BLUE);
        blue.setDescription("Somma dei valori su tutti i dadi blu");
        purple = new PrivateObjectiveCard("Sfumature Viola", 5, Color.PURPLE);
        purple.setDescription("Somma dei valori su tutti i dadi blu");

        privateObjectiveDeck.add(red);
        privateObjectiveDeck.add(green);
        privateObjectiveDeck.add(yellow);
        privateObjectiveDeck.add(blue);
        privateObjectiveDeck.add(purple);


    }

    public ArrayList<PrivateObjectiveCard> getPrivateObjectiveDeck(){
        return this.privateObjectiveDeck;
    }

    public PrivateObjectiveCard getPrivateObjectiveCard(int index) throws NotValidInputException {
        if (index<0 || index>=privateObjectiveDeck.size()){
            throw new NotValidInputException();
        }
        return this.privateObjectiveDeck.get(index);                //attenzione all'indice dell'arraylist = ID-1
    }

    public PrivateObjectiveCard removePrivateObjectiveCard(int index) {
        if(index<0 || index >privateObjectiveDeck.size()) throw new IndexOutOfBoundsException();
        return privateObjectiveDeck.remove(index);
    }

    public int size(){
        return privateObjectiveDeck.size();
    }


    public void dump() {
        for (PrivateObjectiveCard c: privateObjectiveDeck) {
            c.dump();
            System.out.println("\n");
        }
    }

    public String toString() {
        String string= new String();
        for (PrivateObjectiveCard c: privateObjectiveDeck) {
            string= string.concat(c.toString()+"\n\n");
        }
        return string;
    }

}
