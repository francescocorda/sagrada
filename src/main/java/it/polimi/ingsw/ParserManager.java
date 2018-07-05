package it.polimi.ingsw;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.PatternDeck;
import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.Model.Cards.PublicObjectives.*;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.Model.effects.Effect;
import it.polimi.ingsw.Model.effects.EffectFactory;
import it.polimi.ingsw.exceptions.DuplicateException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.Model.Cards.Patterns.PatternCard.ROW;

public class ParserManager {

    private static ParserManager instance = null;
    private JSONParser parser;
    private static final String JSON_PATTERNS_PATH = "./src/main/resources/patterns";
    private static final String JSON_PATTERNS_PARAMETER_2 = "name";
    private static final String JSON_PATTERNS_PARAMETER_3 = "difficulty";
    private static final String JSON_PATTERNS_PARAMETER_4 = "patternCard";
    private static final String JSON_PUB_OBJ_PATH = "src/main/resources/objectives.json";
    private static final String JSON_PUB_OBJ_PATH1 = "/objectives.json";
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
    private static final String JSON_TOOL_CARDS_PATH = "./src/main/resources/toolcards";
    private static final String JSON_TOOL_CARDS_PARAMETER_2 = "ID";
    private static final String JSON_TOOL_CARDS_PARAMETER_3 = "name";
    private static final String JSON_TOOL_CARDS_PARAMETER_4 = "description";
    private static final String JSON_TOOL_CARDS_PARAMETER_5 = "usableInTurns";
    private static final String JSON_TOOL_CARDS_PARAMETER_6 = "movesLeft";
    private static final String JSON_TOOL_CARDS_PARAMETER_7 = "classes";
    private static final String JSON_TOOL_CARDS_PARAMETER_8 = "parameters";

    private Gson gson;


    private ParserManager() {
        parser = new JSONParser();
    }

    public static ParserManager getParserManager() {
        if (instance == null) {
            instance = new ParserManager();
        }
        return instance;
    }

    private ArrayList<String> walkResources(String directory) {
        ArrayList<String> list = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            list.addAll(paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.subpath(4, 6).toString().replace("\\","/"))
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public PatternDeck getPatternDeck(){
        PatternDeck deck = new PatternDeck();
        List<String> list = walkResources(JSON_PATTERNS_PATH);

        if(!list.isEmpty()) {
            for (String path: list) {
                InputStream inputStream = this.getClass().getResourceAsStream("/" + path);
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                JsonParser parser = new JsonParser();
                JsonObject patternJson = parser.parse(reader).getAsJsonObject();
                String name = patternJson.get(JSON_PATTERNS_PARAMETER_2).getAsString();
                PatternCard patternCard = new PatternCard(name, deck.size()+1);
                int difficulty = patternJson.get(JSON_PATTERNS_PARAMETER_3).getAsInt();
                try {
                    patternCard.setDifficulty(difficulty);
                } catch (NotValidInputException e) {
                    System.out.println(e);
                }
                JsonArray card = patternJson.get(JSON_PATTERNS_PARAMETER_4).getAsJsonArray();
                if (card.size() == ROW * COLUMN) {
                    for (int i = 0; i < ROW; i++) {
                        for (int k = 0; k < COLUMN; k++) {
                            patternCard.setRestriction(i + 1, k + 1, Restriction.valueOf(card.get(i * 5 + k).getAsString()));
                        }
                    }
                }
                deck.add(patternCard);
            }
        }
        System.out.println("Pattern deck size: " + deck.size());
        return deck;
    }

    public ArrayList<PublicObjectiveCard> getPublicObjectiveDeck() {
        ArrayList<PublicObjectiveCard> puODeck = new ArrayList<>();
        Object obj = null;
        InputStream inputStream = this.getClass().getResourceAsStream(JSON_PUB_OBJ_PATH1);
        try {
            obj = parser.parse(new InputStreamReader(inputStream));
        } catch (IOException | ParseException e) {
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
        for (int i = 0; i < jRowArray.size(); i++) {
            JSONObject jRowPuOC = (JSONObject) jRowArray.get(i);
            PublicObjectiveCard row = new RowPublicObjectiveCard((String) jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                    puODeck.size() + 1,
                    Integer.valueOf((String) jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                    (String) jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_5));
            row.setDescription((String) jRowPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
            puODeck.add(row);
        }

        JSONArray jColArray = (JSONArray) jPuODeck.get(JSON_PUB_OBJ_PARAMETER_7);
        for (int i = 0; i < jColArray.size(); i++) {
            JSONObject jColPuOC = (JSONObject) jColArray.get(i);
            PublicObjectiveCard col = new ColumnPublicObjectiveCard((String) jColPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                    puODeck.size() + 1,
                    Integer.valueOf((String) jColPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                    (String) jColPuOC.get(JSON_PUB_OBJ_PARAMETER_5));
            col.setDescription((String) jColPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
            puODeck.add(col);
        }
        JSONArray jSetArray = (JSONArray) jPuODeck.get(JSON_PUB_OBJ_PARAMETER_8);
        for (int i = 0; i < jSetArray.size(); i++) {
            JSONObject jSetPuOC = (JSONObject) jSetArray.get(i);
            JSONArray jElementsArray = (JSONArray) jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_9);
            ArrayList<String> elements = new ArrayList<>();
            for (int j = 0; j < jElementsArray.size(); j++) {
                elements.add(Restriction.valueOf((String) jElementsArray.get(j)).escape());
            }
            PublicObjectiveCard set = new SetPublicObjectiveCard((String) jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                    puODeck.size() + 1,
                    Integer.valueOf((String) jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                    (String) jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_5), elements);
            set.setDescription((String) jSetPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
            puODeck.add(set);
        }
        JSONObject jDiagonalPuOC = (JSONObject) jPuODeck.get(JSON_PUB_OBJ_PARAMETER_10);
        PublicObjectiveCard diagonal = new DiagonalPublicObjectiveCard((String) jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_3),
                puODeck.size() + 1,
                Integer.valueOf((String) jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_4)),
                (String) jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_5));
        diagonal.setDescription((String) jDiagonalPuOC.get(JSON_PUB_OBJ_PARAMETER_6));
        puODeck.add(diagonal);
        return puODeck;
    }

    public ArrayList<ToolCard> getToolCards(){
        ArrayList<ToolCard> toolCards = new ArrayList<>();
        ArrayList<String> fileList = walkResources(JSON_TOOL_CARDS_PATH);
        Gson gson = new Gson();
        if(!fileList.isEmpty()) {
            for (String path : fileList) {
                InputStream inputStream = this.getClass().getResourceAsStream("/" + path);
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                JsonParser parser = new JsonParser();
                JsonObject card = parser.parse(reader).getAsJsonObject();
                int ID = gson.fromJson(card.get(JSON_TOOL_CARDS_PARAMETER_2), Integer.class);
                String name = gson.fromJson(card.get(JSON_TOOL_CARDS_PARAMETER_3), String.class);
                String description = gson.fromJson(card.get(JSON_TOOL_CARDS_PARAMETER_4), String.class);
                int[] turnsArray = gson.fromJson(card.get(JSON_TOOL_CARDS_PARAMETER_5), int[].class);
                ArrayList<Integer> turnsList = new ArrayList<>();
                for (int i : turnsArray) {
                    turnsList.add(i);
                }
                int[] movesArray = gson.fromJson(card.get(JSON_TOOL_CARDS_PARAMETER_6), int[].class);
                ArrayList<Integer> movesList = new ArrayList<>();
                for (int i : movesArray) {
                    movesList.add(i);
                }

                String[] classes = gson.fromJson(card.get(JSON_TOOL_CARDS_PARAMETER_7), String[].class);
                String[] parameters = gson.fromJson(card.get(JSON_TOOL_CARDS_PARAMETER_8), String[].class);
                EffectFactory factory = new EffectFactory();
                ArrayList<Effect> effects = new ArrayList<>();
                for (int i = 0; i < classes.length; i++) {
                    effects.add(factory.createEffect(classes[i], parameters[i]));
                }
                ToolCard toolCard = new ToolCard(ID, name, description, turnsList, movesList);
                toolCard.setEffects(effects);
                int i = 0;
                while (i<toolCards.size() && toolCards.get(i).getID()<toolCard.getID()) {
                    i++;
                }
                if (i==toolCards.size()) {
                    toolCards.add(toolCard);
                } else if (toolCards.get(i).getID()!=toolCard.getID()) {
                    toolCards.add(i, toolCard);
                }
            }
        }
        return toolCards;
    }

}

