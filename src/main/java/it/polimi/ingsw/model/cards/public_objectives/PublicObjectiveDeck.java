package it.polimi.ingsw.model.cards.public_objectives;

import it.polimi.ingsw.exceptions.NotValidInputException;
import java.io.Serializable;
import java.util.ArrayList;

public class PublicObjectiveDeck implements Serializable {
    private ArrayList<PublicObjectiveCard> puODeck;

    /**
     * creates a new {@link PublicObjectiveDeck} from the given {@link ArrayList<PublicObjectiveCard>} deck.
     * @param deck : the given {@link ArrayList<PublicObjectiveCard>} deck
     */
    public PublicObjectiveDeck(ArrayList<PublicObjectiveCard> deck){
        puODeck = deck;
    }

    /**
     * @param index : the given int index
     * @return the {@link PublicObjectiveCard} in {@link #puODeck} at the given int index.
     * @throws NotValidInputException if the given index is not valid
     */
    public PublicObjectiveCard getPuOC(int index) throws NotValidInputException {
        if (index<0 || index>=puODeck.size()){
            throw new NotValidInputException();
        }
        return this.puODeck.get(index);
    }

    /**
     * @param index : the given int index
     * @return the {@link PublicObjectiveCard} in {@link #puODeck} at the given int index, then remove it
     */
    public PublicObjectiveCard removePuOC(int index) {
        if(index<0 || index >puODeck.size()) throw new IndexOutOfBoundsException();
        return puODeck.remove(index);
    }

    /**
     * @return the int size of {@link #puODeck}
     */
    public int size(){
        return puODeck.size();
    }

    /**
     * @return the {@link String} representation of this {@link PublicObjectiveDeck}
     */
    @Override
    public String toString() {
        String string= "";
        for (PublicObjectiveCard c: puODeck) {
            string= string.concat(c.toString()+"\n\n");
        }
        return string;
    }

    /**
     * displays {@link #toString()}.
     */
    public void dump() {
        System.out.println(toString());
    }
}