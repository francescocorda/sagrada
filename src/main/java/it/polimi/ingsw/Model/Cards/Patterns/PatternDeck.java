package it.polimi.ingsw.Model.Cards.Patterns;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Integer.valueOf;


public class PatternDeck implements Serializable {

    private ArrayList<PatternCard> deck;

    public PatternDeck() {
        deck=new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader("src/main/resources/patterns.json"));
        } catch (IOException e) {
            System.out.println(e);
        } catch (ParseException e) {
            System.out.println(e);
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jpatternDeck;
        if (jsonObject != null) {
            jpatternDeck = (JSONArray) jsonObject.get("patternDeck");
        } else {
            throw new NullPointerException();
        }
        for(int j=0; j<24; j++){
            JSONObject jpatternCard = (JSONObject) jpatternDeck.get(j);
            PatternCard patternCard = new PatternCard((String) jpatternCard.get("name"), j+1);
            try {
                patternCard.setDifficulty(valueOf((String)jpatternCard.get("difficulty")));
            } catch (NotValidInputException e) {
                System.out.println(e);
            }
            JSONArray card = (JSONArray) jpatternCard.get("patternCard");
            int length = card.size();
            String [] patterns = new String [length];

            if (length > 0) {
                for (int i = 0; i < 4; i++) {
                    for(int k=0; k<5; k++){
                        patternCard.setRestriction(i+1, k+1, Restriction.valueOf((String)card.get(i*5+k)));
                    }
                }
            }
            deck.add(patternCard);
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



