package it.polimi.ingsw.Model.Cards.PublicObjectives;

import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.exceptions.NotValidInputException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PublicObjectiveDeck {
    private ArrayList<PublicObjectiveCard> puODeck;
    private static final Logger LOGGER = Logger.getLogger(PublicObjectiveDeck.class.getName());

    public PublicObjectiveDeck() {
        puODeck = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("src/main/resources/objectives.json"));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject jPuODeck;
        if (jsonObject != null) {
            jPuODeck = (JSONObject) jsonObject.get("objectiveDeck");
        } else {
            throw new NullPointerException();
        }

        JSONArray jRowArray = (JSONArray) jPuODeck.get("row");
        for(int i=0; i<jRowArray.size(); i++) {
            JSONObject jRowPuOC = (JSONObject) jRowArray.get(i);
            PublicObjectiveCard row = new RowPublicObjectiveCard((String)jRowPuOC.get("name"),
                    puODeck.size()+1,
                    Integer.valueOf((String)jRowPuOC.get("points")),
                    (String)jRowPuOC.get("restriction"));
            row.setDescription((String)jRowPuOC.get("description"));
            puODeck.add(row);
        }

        JSONArray jColArray = (JSONArray) jPuODeck.get("column");
        for(int i=0; i<jColArray.size(); i++) {
            JSONObject jColPuOC = (JSONObject) jColArray.get(i);
            PublicObjectiveCard col = new ColumnPublicObjectiveCard((String)jColPuOC.get("name"),
                    puODeck.size()+1,
                    Integer.valueOf((String)jColPuOC.get("points")),
                    (String)jColPuOC.get("restriction"));
            col.setDescription((String)jColPuOC.get("description"));
            puODeck.add(col);
        }
        JSONArray jSetArray = (JSONArray) jPuODeck.get("set");
        for(int i=0; i<jSetArray.size(); i++) {
            JSONObject jSetPuOC = (JSONObject) jSetArray.get(i);
            JSONArray jElementsArray = (JSONArray) jSetPuOC.get("elements");
            ArrayList<String> elements = new ArrayList<>();
            for(int j=0; j<jElementsArray.size(); j++) {
                elements.add(Restriction.valueOf((String) jElementsArray.get(j)).escape());
            }
            PublicObjectiveCard set = new SetPublicObjectiveCard((String)jSetPuOC.get("name"),
                    puODeck.size()+1,
                    Integer.valueOf((String)jSetPuOC.get("points")),
                    (String)jSetPuOC.get("restriction"),
                    elements);
            set.setDescription((String)jSetPuOC.get("description"));
            puODeck.add(set);
        }
        JSONObject jDiagonalPuOC = (JSONObject) jPuODeck.get("diagonal");
        PublicObjectiveCard diagonal = new DiagonalPublicObjectiveCard((String)jDiagonalPuOC.get("name"),
                puODeck.size()+1,
                Integer.valueOf((String)jDiagonalPuOC.get("points")),
                (String)jDiagonalPuOC.get("restriction"));
        diagonal.setDescription((String)jDiagonalPuOC.get("description"));
        puODeck.add(diagonal);

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