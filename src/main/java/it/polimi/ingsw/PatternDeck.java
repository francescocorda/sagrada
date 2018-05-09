package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NotValidInputException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static java.lang.Integer.valueOf;


public class PatternDeck {

    private ArrayList<PatternCard> patternDeck;
    Logger logger = Logger.getLogger(PatternDeck.class.getName());

    public PatternDeck() {
        patternDeck=new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader("src/main/java/it/polimi/ingsw/patterns.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jpatternDeck = (JSONArray) jsonObject.get("patternDeck");
        for(int j=0; j<24; j++){
            JSONObject jpatternCard = (JSONObject) jpatternDeck.get(j);
            PatternCard patternCard = new PatternCard((String) jpatternCard.get("name"), j+1);
            try {
                patternCard.setDifficulty(valueOf((String)jpatternCard.get("difficulty")));
            } catch (NotValidInputException e) {
                e.printStackTrace();
            }
            JSONArray card = (JSONArray) jpatternCard.get("patternCard");
            int length = card.size();
            String [] patterns = new String [length];

            if (length > 0) {
                for (int i = 0; i < 4; i++) {
                    for(int k=0; k<5; k++){
                        patternCard.setRestriction(i+1, k+1,Restriction.valueOf((String)card.get(i*5+k)));
                    }
                }
            }
            patternDeck.add(patternCard);
        }
    }

    public ArrayList<PatternCard> getPatternDeck(){
        return this.patternDeck;
    }

    public PatternCard getPatternCard(int id) throws NotValidInputException {   //l'ho aggiunta per avere un controllo sul parametro
        if (id<0 || id>=patternDeck.size()){                                 // ma non so se può servire, ho visto che per ora
            throw new NotValidInputException();                                    //avete usato la getPatternDeck().get(index).
        }                                                                          //Nel caso non serva toglietela pure
        return this.patternDeck.get(id-1);
    }

    public PatternCard removePatternCard(int index) {
        if(index<0 || index >patternDeck.size()) throw new IndexOutOfBoundsException();
        return patternDeck.remove(index);
    }

    public int size(){
        return patternDeck.size();
    }


    public void dump() {
        for (PatternCard c: patternDeck) {
            c.dump();
            System.out.println("\n");
        }
    }

    @Override
    public String toString() {
        String string= "";
        for (PatternCard c: patternDeck) {
            string= string.concat(c.toString()+"\n\n");
        }
        return string;
    }
}



