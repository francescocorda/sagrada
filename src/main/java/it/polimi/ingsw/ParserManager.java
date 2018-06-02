package it.polimi.ingsw;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.Model.Cards.PublicObjectives.*;
import it.polimi.ingsw.exceptions.NotValidInputException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.ROW;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternDeck.PATTERN_CARD_NUMBER;
import static java.lang.Integer.valueOf;

public class ParserManager {

    private static ParserManager instance = null;
    private JSONParser parser;
    private static final String JSON_PATTERNS_PATH = "src/main/resources/patterns.json";
    private static final String JSON_PATTERNS_PARAMETER_1 = "patternDeck";
    private static final String JSON_PATTERNS_PARAMETER_2 = "name";
    private static final String JSON_PATTERNS_PARAMETER_3 = "difficulty";
    private static final String JSON_PATTERNS_PARAMETER_4 = "patternCard";
    private static final String JSON_PUB_OBJ_PATH = "src/main/resources/objectives.json";
    private static final String JSON_PUB_OBJ_PARAMETER_1 = "objectiveDeck";
    private static final String JSON_PUB_OBJ_PARAMETER_2 = "row";
    private static final String JSON_PUB_OBJ_PARAMETER_3 = "name";
    private static final String JSON_PUB_OBJ_PARAMETER_4 = "points";
    private static final String JSON_PUB_OBJ_PARAMETER_5 = "restriction";
    private static final String JSON_PUB_OBJ_PARAMETER_6 = "description";
    private static final String JSON_PUB_OBJ_PARAMETER_7 = "column";
    private static final String JSON_PUB_OBJ_PARAMETER_8 = "set";
    private static final String JSON_PUB_OBJ_PARAMETER_9 = "elements";
    private static final String JSON_PUB_OBJ_PARAMETER_10 = "diagonal";

    private ParserManager(){
        parser = new JSONParser();
    }

    public static ParserManager getParserManager(){
        if(instance == null){
            instance = new ParserManager();
        }
        return instance;
    }


    public ArrayList<PatternCard> getPatternDeck(){
        ArrayList<PatternCard> deck = new ArrayList<>();
        Object obj=null;
        try {
            obj = parser.parse(new FileReader(JSON_PATTERNS_PATH));
        } catch (IOException e) {
            System.out.println(e);
        } catch (ParseException e) {
            System.out.println(e);
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jpatternDeck;
        if (jsonObject != null) {
            jpatternDeck = (JSONArray) jsonObject.get(JSON_PATTERNS_PARAMETER_1);
        } else {
            throw new NullPointerException();
        }
        for(int j=0; j<PATTERN_CARD_NUMBER; j++){
            JSONObject jpatternCard = (JSONObject) jpatternDeck.get(j);
            PatternCard patternCard = new PatternCard((String) jpatternCard.get(JSON_PATTERNS_PARAMETER_2), j+1);
            try {
                patternCard.setDifficulty(valueOf((String)jpatternCard.get(JSON_PATTERNS_PARAMETER_3)));
            } catch (NotValidInputException e) {
                System.out.println(e);
            }
            JSONArray card = (JSONArray) jpatternCard.get(JSON_PATTERNS_PARAMETER_4);
            int length = card.size();
            if (length > 0) {
                for (int i = 0; i < ROW; i++) {
                    for(int k=0; k<COLUMN; k++){
                        patternCard.setRestriction(i+1, k+1, Restriction.valueOf((String)card.get(i*5+k)));
                    }
                }
            }
            deck.add(patternCard);
        }
        return  deck;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveDeck() {
        ArrayList<PublicObjectiveCard> puODeck = new ArrayList<>();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader(JSON_PUB_OBJ_PATH));
        } catch (IOException e) {
            System.out.println(e);
        } catch (ParseException e) {
            System.out.println(e);
        }
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject jPuODeck;
        if (jsonObject != null) {
            jPuODeck = (JSONObject) jsonObject.get(JSON_PUB_OBJ_PARAMETER_1);
        } else {
            throw new NullPointerException();
        }

        JSONArray jRowArray = (JSONArray) jPuODeck.get(JSON_PUB_OBJ_PARAMETER_2);
        for(int i=0; i<jRowArray.size(); i++) {
            JSONObject jRowPuOC = (JSONObject) jRowArray.get(i);
            PublicObjectiveCard row = new RowPublicObjectiveCard((String)jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                    puODeck.size()+1,
                    Integer.valueOf((String)jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                    (String)jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_5));
            row.setDescription((String)jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
            puODeck.add(row);
        }

        JSONArray jColArray = (JSONArray) jPuODeck.get(JSON_PUB_OBJ_PARAMETER_7);
        for(int i=0; i<jColArray.size(); i++) {
            JSONObject jColPuOC = (JSONObject) jColArray.get(i);
            PublicObjectiveCard col = new ColumnPublicObjectiveCard((String)jColPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                    puODeck.size()+1,
                    Integer.valueOf((String)jColPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                    (String)jColPuOC.get(JSON_PUB_OBJ_PARAMETER_5));
            col.setDescription((String)jColPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
            puODeck.add(col);
        }
        JSONArray jSetArray = (JSONArray) jPuODeck.get(JSON_PUB_OBJ_PARAMETER_8);
        for(int i=0; i<jSetArray.size(); i++) {
            JSONObject jSetPuOC = (JSONObject) jSetArray.get(i);
            JSONArray jElementsArray = (JSONArray) jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_9);
            ArrayList<String> elements = new ArrayList<>();
            for(int j=0; j<jElementsArray.size(); j++) {
                elements.add(Restriction.valueOf((String) jElementsArray.get(j)).escape());
            }
            PublicObjectiveCard set = new SetPublicObjectiveCard((String)jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                    puODeck.size()+1,
                    Integer.valueOf((String)jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                    (String)jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_5), elements);
            set.setDescription((String)jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
            puODeck.add(set);
        }
        JSONObject jDiagonalPuOC = (JSONObject) jPuODeck.get(JSON_PUB_OBJ_PARAMETER_10);
        PublicObjectiveCard diagonal = new DiagonalPublicObjectiveCard((String)jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                puODeck.size()+1,
                Integer.valueOf((String)jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                (String)jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_5));
        diagonal.setDescription((String)jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
        puODeck.add(diagonal);
        return  puODeck;
    }
}
