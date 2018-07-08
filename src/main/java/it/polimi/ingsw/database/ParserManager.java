package it.polimi.ingsw.database;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.patterns.PatternDeck;
import it.polimi.ingsw.model.cards.patterns.Restriction;
import it.polimi.ingsw.model.cards.public_objectives.*;
import it.polimi.ingsw.model.cards.toolcard.ToolCard;
import it.polimi.ingsw.model.effects.Effect;
import it.polimi.ingsw.model.effects.EffectFactory;
import it.polimi.ingsw.exceptions.NotValidInputException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.polimi.ingsw.model.cards.patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.model.cards.patterns.PatternCard.ROW;

public class ParserManager {

    private static ParserManager instance = null;
    private static final String JSON_PATTERNS_PATH = "./src/main/resources/patterns";
    private static final String JSON_PATTERNS_PARAMETER_1 = "id";
    private static final String JSON_PATTERNS_PARAMETER_2 = "name";
    private static final String JSON_PATTERNS_PARAMETER_3 = "difficulty";
    private static final String JSON_PATTERNS_PARAMETER_4 = "patternCard";
    private static final String JSON_PUB_OBJ_PATH = "./src/main/resources/objectives";
    private static final String JSON_PUB_OBJ_PARAMETER_1 = "type";
    private static final String JSON_PUB_OBJ_PARAMETER_2 = "row";
    private static final String JSON_PUB_OBJ_PARAMETER_3 = "name";
    private static final String JSON_PUB_OBJ_PARAMETER_4 = "points";
    private static final String JSON_PUB_OBJ_PARAMETER_5 = "restriction";
    private static final String JSON_PUB_OBJ_PARAMETER_6 = "description";
    private static final String JSON_PUB_OBJ_PARAMETER_7 = "column";
    private static final String JSON_PUB_OBJ_PARAMETER_8 = "set";
    private static final String JSON_PUB_OBJ_PARAMETER_9 = "elements";
    private static final String JSON_PUB_OBJ_PARAMETER_10 = "diagonal";
    private static final String JSON_PUB_OBJ_PARAMETER_11 = "ID";
    private static final String JSON_TOOL_CARDS_PATH = "./src/main/resources/toolcards";
    private static final String JSON_TOOL_CARDS_PARAMETER_2 = "ID";
    private static final String JSON_TOOL_CARDS_PARAMETER_3 = "name";
    private static final String JSON_TOOL_CARDS_PARAMETER_4 = "description";
    private static final String JSON_TOOL_CARDS_PARAMETER_5 = "usableInTurns";
    private static final String JSON_TOOL_CARDS_PARAMETER_6 = "movesLeft";
    private static final String JSON_TOOL_CARDS_PARAMETER_7 = "classes";
    private static final String JSON_TOOL_CARDS_PARAMETER_8 = "parameters";

    /**
     * @return the only instance of the {@link ParserManager}
     */
    public static ParserManager getParserManager() {
        if (instance == null) {
            instance = new ParserManager();
        }
        return instance;
    }

    /**
     * @param directory : the given {@link String} directory
     * @return an {@link ArrayList<String>} of File's names in the given {@link String} directory
     */
    private ArrayList<String> walkResources(String directory) {
        ArrayList<String> list = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            list.addAll(paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.normalize().toString())
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @return the {@link PatternDeck}.
     */
    public PatternDeck getPatternDeck(){
        PatternDeck deck = new PatternDeck();
        List<String> list = walkResources(JSON_PATTERNS_PATH);
        if(!list.isEmpty()) {
            for (String path: list) {
                InputStream inputStream;
                try {
                    inputStream = new FileInputStream(path);
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                    JsonParser parser = new JsonParser();
                    JsonObject patternJson = parser.parse(reader).getAsJsonObject();
                    String name = patternJson.get(JSON_PATTERNS_PARAMETER_2).getAsString();
                    String ID = patternJson.get(JSON_PATTERNS_PARAMETER_1).getAsString();
                    PatternCard patternCard = new PatternCard(name, Integer.valueOf(ID));
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
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Pattern deck size: " + deck.size());
        return deck;
    }

    /**
     * @return an {@link ArrayList<PublicObjectiveCard>}.
     */
    public ArrayList<PublicObjectiveCard> getPublicObjectiveDeck() {
        ArrayList<PublicObjectiveCard> puODeck = new ArrayList<>();
        List<String> list = walkResources(JSON_PUB_OBJ_PATH);
        if (!list.isEmpty()) {
            for (String path : list) {
                InputStream inputStream;
                try {
                    inputStream = new FileInputStream(path);
                    JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                    JsonParser parser = new JsonParser();
                    JsonObject objectiveJson = parser.parse(reader).getAsJsonObject();
                    String type = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_1).getAsString();
                    PublicObjectiveCard puOC = new PublicObjectiveCard();
                    boolean initialized = false;
                    if (type.equals(JSON_PUB_OBJ_PARAMETER_2)) {
                        String name = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_3).getAsString();
                        int id = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_11).getAsInt();
                        int points = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_4).getAsInt();
                        String restriction = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_5).getAsString();
                        String description = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_6).getAsString();
                        puOC = new RowPublicObjectiveCard(name, id, points, restriction);
                        puOC.setDescription(description);
                        initialized = true;
                    } else if (type.equals(JSON_PUB_OBJ_PARAMETER_7)) {
                        String name = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_3).getAsString();
                        int id = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_11).getAsInt();
                        int points = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_4).getAsInt();
                        String restriction = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_5).getAsString();
                        String description = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_6).getAsString();
                        puOC = new ColumnPublicObjectiveCard(name, id, points, restriction);
                        puOC.setDescription(description);
                        initialized = true;
                    } else if (type.equals(JSON_PUB_OBJ_PARAMETER_8)) {
                        String name = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_3).getAsString();
                        int id = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_11).getAsInt();
                        int points = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_4).getAsInt();
                        String restriction = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_5).getAsString();
                        String description = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_6).getAsString();
                        ArrayList<String> elements = new ArrayList<>();
                        JsonArray jElementsArray = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_9).getAsJsonArray();
                        for (int j = 0; j < jElementsArray.size(); j++) {
                            elements.add(Restriction.valueOf(jElementsArray.get(j).getAsString()).escape());
                        }
                        puOC = new SetPublicObjectiveCard(name, id, points, restriction, elements);
                        puOC.setDescription(description);
                        initialized = true;
                    } else if (type.equals(JSON_PUB_OBJ_PARAMETER_10)) {
                        String name = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_3).getAsString();
                        int id = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_11).getAsInt();
                        int points = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_4).getAsInt();
                        String restriction = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_5).getAsString();
                        String description = objectiveJson.get(JSON_PUB_OBJ_PARAMETER_6).getAsString();
                        puOC = new DiagonalPublicObjectiveCard(name, id, points, restriction);
                        puOC.setDescription(description);
                        initialized = true;
                    }
                    if (initialized) {
                        int i = 0;
                        while (i < puODeck.size() && puODeck.get(i).getID() < puOC.getID()) {
                            i++;
                        }
                        if (i == puODeck.size()) {
                            puODeck.add(puOC);
                        } else if (puODeck.get(i).getID() != puOC.getID()) {
                            puODeck.add(i, puOC);
                        }
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Public Objective Deck Size: " + puODeck.size());
        return puODeck;
    }

    /**
     * @return an {@link ArrayList<ToolCard>}.
     */
    public ArrayList<ToolCard> getToolCards(){
        ArrayList<ToolCard> toolCards = new ArrayList<>();
        ArrayList<String> fileList = walkResources(JSON_TOOL_CARDS_PATH);
        Gson gson = new Gson();
        if(!fileList.isEmpty()) {
            for (String path : fileList) {
                InputStream inputStream;
                try {
                    inputStream = new FileInputStream(path);
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
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Tool Cards Deck Size: " + toolCards.size());
        return toolCards;
    }

}

