package it.polimi.ingsw.Model.Cards.PublicObjectives;

import it.polimi.ingsw.exceptions.NotValidInputException;
import java.io.Serializable;
import java.util.ArrayList;

public class PublicObjectiveDeck implements Serializable {
    private ArrayList<PublicObjectiveCard> puODeck;

    public PublicObjectiveDeck(ArrayList<PublicObjectiveCard> deck){
        puODeck = deck;
    }

    public PublicObjectiveCard getPuOC(int index) throws NotValidInputException {
        if (index<0 || index>=puODeck.size()){
            throw new NotValidInputException();
        }
        return this.puODeck.get(index);                //attenzione all'indice dell'arraylist = ID-1
    }

    public PublicObjectiveCard removePuOC(int index) {
        if(index<0 || index >puODeck.size()) throw new IndexOutOfBoundsException();
        return puODeck.remove(index);
    }

    public int size(){
        return puODeck.size();
    }

    @Override
    public String toString() {
        String string= "";
        for (PublicObjectiveCard c: puODeck) {
            string= string.concat(c.toString()+"\n\n");
        }
        return string;
    }

    public void dump() {
        System.out.println(toString());
    }
}