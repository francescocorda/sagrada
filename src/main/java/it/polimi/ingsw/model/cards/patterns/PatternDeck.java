package it.polimi.ingsw.model.cards.patterns;

import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.valueOf;


public class PatternDeck implements Serializable {

    public static final int PATTERN_CARD_NUMBER = 24;
    private ArrayList<PatternCard> deck;

    public PatternDeck() {
        deck = new ArrayList<>();
    }

    public PatternDeck(PatternDeck patternDeck) {
        this.deck = new ArrayList<>(patternDeck.deck);
    }

    public void add(PatternCard patternCard) {
        if (deck.isEmpty()) {
            deck.add(patternCard);
        } else {
            int i = 0;
            while (i<deck.size() && deck.get(i).getID()<patternCard.getID()) {
                i++;
            }
            if (i == deck.size()) {
                deck.add(patternCard);
            } else if (deck.get(i).getID()!=patternCard.getID()) {
                deck.add(i,patternCard);
            }
        }
    }

    public ArrayList<PatternCard> getPatternDeck(){
        return this.deck;
    }

    public PatternCard getPatternCard(int index) throws NotValidInputException {   //l'ho aggiunta per avere un controllo sul parametro
        if (index<0 || index>=deck.size()){                                 // ma non so se può servire, ho visto che per ora
            throw new NotValidInputException();                                    //avete usato la getPatternDeck().get(index).
        }                                                                          //Nel caso non serva toglietela pure
        return this.deck.get(index);
    }

    public PatternCard removePatternCard(int index) {
        if(index<0 || index >deck.size()) throw new IndexOutOfBoundsException();
        return deck.remove(index);
    }

    public int size(){
        return deck.size();
    }


    public void dump() {
        for (PatternCard c: deck) {
            c.dump();
            System.out.println("\n");
        }
    }

    @Override
    public String toString() {
        String string= "";
        for (PatternCard c: deck) {
            string= string.concat(c.toString()+"\n\n");
        }
        return string;
    }
}



