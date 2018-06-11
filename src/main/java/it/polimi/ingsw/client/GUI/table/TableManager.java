package it.polimi.ingsw.client.GUI.table;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.GUI.GUIManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TableManager implements GUIManager {
    private HashMap<String, Integer> comparator = null;
    private HashMap<Integer, String> PUOCs = null;  //Public Objective Cards
    private HashMap<Integer, String> tools = null;  //Toolcards
    private HashMap<Integer, String> PVOCs = null;  //Private Objective Cards
    private HashMap<Integer, String> dices = null;
    private HashMap<Color, String> colors = null;  //Restriction of colors
    private HashMap<String, String> colorsWindow = null;
    private ArrayList<Rectangle> cells1 = null;  //cells of player one
    private ArrayList<Rectangle> cells2 = null;  //cells of player two
    private ArrayList<Rectangle> cells3 = null;  //cells of player three
    private ArrayList<Rectangle> cells4 = null;  //cells of player four
    private ArrayList<StackPane> poolItems = null;  //dices of draftPool
    private ArrayList<StackPane> window1Items = null; //dices to remove from window1
    private ArrayList<StackPane> window2Items = null; //dices to remove from window2
    private ArrayList<StackPane> window3Items = null; //dices to remove from window3
    private ArrayList<StackPane> window4Items = null; //dices to remove from window4
    private ArrayList<Rectangle> cellsPool = null;  //cells of draftPool
    private Table table = null;
    private Rectangle source = null;
    private int idPool = 0;
    private boolean sourceSelectedWindow = false;
    private boolean sourceSelectedPool = false;
    private int sourcerow;
    private int sourcecol;
    private int destrow;
    private int destcol;
    private Communicator communicator;
    private boolean endGame = false;
    private boolean move;
    private boolean toolCard;
    @FXML GridPane draftPool;
    @FXML Rectangle dice1;
    @FXML Rectangle dice2;
    @FXML Rectangle dice3;
    @FXML Rectangle dice4;
    @FXML Rectangle dice5;
    @FXML Rectangle dice6;
    @FXML Rectangle dice7;
    @FXML Rectangle dice8;
    @FXML Rectangle dice9;
    //window1
    @FXML GridPane window1;
    @FXML Rectangle dice11;
    @FXML Rectangle dice12;
    @FXML Rectangle dice13;
    @FXML Rectangle dice14;
    @FXML Rectangle dice15;
    @FXML Rectangle dice21;
    @FXML Rectangle dice22;
    @FXML Rectangle dice23;
    @FXML Rectangle dice24;
    @FXML Rectangle dice25;
    @FXML Rectangle dice31;
    @FXML Rectangle dice32;
    @FXML Rectangle dice33;
    @FXML Rectangle dice34;
    @FXML Rectangle dice35;
    @FXML Rectangle dice41;
    @FXML Rectangle dice42;
    @FXML Rectangle dice43;
    @FXML Rectangle dice44;
    @FXML Rectangle dice45;
    //window2
    @FXML GridPane window2;
    @FXML TextArea username2;
    @FXML Rectangle dice2_11;
    @FXML Rectangle dice2_12;
    @FXML Rectangle dice2_13;
    @FXML Rectangle dice2_14;
    @FXML Rectangle dice2_15;
    @FXML Rectangle dice2_21;
    @FXML Rectangle dice2_22;
    @FXML Rectangle dice2_23;
    @FXML Rectangle dice2_24;
    @FXML Rectangle dice2_25;
    @FXML Rectangle dice2_31;
    @FXML Rectangle dice2_32;
    @FXML Rectangle dice2_33;
    @FXML Rectangle dice2_34;
    @FXML Rectangle dice2_35;
    @FXML Rectangle dice2_41;
    @FXML Rectangle dice2_42;
    @FXML Rectangle dice2_43;
    @FXML Rectangle dice2_44;
    @FXML Rectangle dice2_45;
    //window3
    @FXML GridPane window3;
    @FXML TextArea username3;
    @FXML Rectangle dice3_11;
    @FXML Rectangle dice3_12;
    @FXML Rectangle dice3_13;
    @FXML Rectangle dice3_14;
    @FXML Rectangle dice3_15;
    @FXML Rectangle dice3_21;
    @FXML Rectangle dice3_22;
    @FXML Rectangle dice3_23;
    @FXML Rectangle dice3_24;
    @FXML Rectangle dice3_25;
    @FXML Rectangle dice3_31;
    @FXML Rectangle dice3_32;
    @FXML Rectangle dice3_33;
    @FXML Rectangle dice3_34;
    @FXML Rectangle dice3_35;
    @FXML Rectangle dice3_41;
    @FXML Rectangle dice3_42;
    @FXML Rectangle dice3_43;
    @FXML Rectangle dice3_44;
    @FXML Rectangle dice3_45;
    //window4
    @FXML GridPane window4;
    @FXML TextArea username4;
    @FXML Rectangle dice4_11;
    @FXML Rectangle dice4_12;
    @FXML Rectangle dice4_13;
    @FXML Rectangle dice4_14;
    @FXML Rectangle dice4_15;
    @FXML Rectangle dice4_21;
    @FXML Rectangle dice4_22;
    @FXML Rectangle dice4_23;
    @FXML Rectangle dice4_24;
    @FXML Rectangle dice4_25;
    @FXML Rectangle dice4_31;
    @FXML Rectangle dice4_32;
    @FXML Rectangle dice4_33;
    @FXML Rectangle dice4_34;
    @FXML Rectangle dice4_35;
    @FXML Rectangle dice4_41;
    @FXML Rectangle dice4_42;
    @FXML Rectangle dice4_43;
    @FXML Rectangle dice4_44;
    @FXML Rectangle dice4_45;
    @FXML TextArea text;
    //Cards
    @FXML ImageView tool1;
    @FXML ImageView tool2;
    @FXML ImageView tool3;
    @FXML ImageView publicObj1;
    @FXML ImageView publicObj2;
    @FXML ImageView publicObj3;
    @FXML ImageView privateObj;
    @FXML Button moveButton;
    @FXML Button toolCardButton;
    @FXML Button skipButton;

    @FXML
    public void mousePressedWindow(MouseEvent e) {
        int row = 7, col = 7;
        source = (Rectangle) e.getSource();
        for (int j = 1; j < 5; j++) {
            for (int k = 1; k < 6; k++) {
                if (source.equals(cells1.get((j - 1) * (5) + (k - 1)))) {
                    row = j;
                    col = k;
                }
            }
        }
        try {
            communicator.sendMessage(row+"/"+col);
        } catch (NetworkErrorException e1) {
            e1.printStackTrace();
        }
        e.consume();
    }
    @FXML
    public void mousePressedPool(MouseEvent e) {
        source = (Rectangle) e.getSource();
        for(int i=0; i<9; i++){
           if (source == cellsPool.get(i)) {
               idPool = i+1;
           }
        }
        try {
            communicator.sendMessage(""+idPool);
        } catch (NetworkErrorException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void moveAction(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("move");
            move=true;
            toolCard = false;
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void toolCardAction(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("toolcard");
            toolCard=true;
            move=false;
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void skipAction(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("skip");
            move=false;
            toolCard=false;
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    public void initialize() throws IOException {
        GUIData.getGUIData().getView().setGUIManager(this);
        StackPane dice;
        text.setEditable(false);
        PUOCs = new HashMap<Integer, String>();
        String image;
        image = "/GUI/publicObj1.PNG";
        PUOCs.put(1, image);
        image = "/GUI/publicObj3.PNG";
        PUOCs.put(2, image);
        image = "/GUI/publicObj2.PNG";
        PUOCs.put(3, image);
        image = "/GUI/publicObj4.PNG";
        PUOCs.put(4, image);
        image = "/GUI/publicObj5.PNG";
        PUOCs.put(5, image);
        image = "/GUI/publicObj6.PNG";
        PUOCs.put(6, image);
        image = "/GUI/publicObj7.PNG";
        PUOCs.put(7, image);
        image = "/GUI/publicObj8.PNG";
        PUOCs.put(8, image);
        image = "/GUI/publicObj9.PNG";
        PUOCs.put(9, image);
        image = "/GUI/publicObj10.PNG";
        PUOCs.put(10, image);
        tools = new HashMap<Integer, String>();
        image = "/GUI/tool1.PNG";
        tools.put(1, image);
        image = "/GUI/tool2.PNG";
        tools.put(2, image);
        image = "/GUI/tool3.PNG";
        tools.put(3, image);
        image = "/GUI/tool4.PNG";
        tools.put(4, image);
        image = "/GUI/tool5.PNG";
        tools.put(5, image);
        image = "/GUI/tool6.PNG";
        tools.put(6, image);
        image = "/GUI/tool7.PNG";
        tools.put(7, image);
        image = "/GUI/tool8.PNG";
        tools.put(8, image);
        image = "/GUI/tool9.PNG";
        tools.put(9, image);
        image = "/GUI/tool10.PNG";
        tools.put(10, image);
        image = "/GUI/tool11.PNG";
        tools.put(11, image);
        image = "/GUI/tool12.PNG";
        tools.put(12, image);
        PVOCs = new HashMap<Integer, String>();
        image = "/GUI/privateObj1.PNG";
        PVOCs.put(1, image);
        image = "/GUI/privateObj2.PNG";
        PVOCs.put(2, image);
        image = "/GUI/privateObj3.PNG";
        PVOCs.put(3, image);
        image = "/GUI/privateObj4.PNG";
        PVOCs.put(4, image);
        image = "/GUI/privateObj5.PNG";
        PVOCs.put(5, image);
        dices = new HashMap<Integer, String>();
        image = "/GUI/dice1.fxml";
        dices.put(1, image);
        image = "/GUI/dice2.fxml";
        dices.put(2, image);
        image = "/GUI/dice3.fxml";
        dices.put(3, image);
        image = "/GUI/dice4.fxml";
        dices.put(4, image);
        image = "/GUI/dice5.fxml";
        dices.put(5, image);
        image = "/GUI/dice6.fxml";
        dices.put(6, image);
        colors = new HashMap<>();
        colors.put(Color.ANSI_YELLOW, "-fx-background-color: rgba(255, 230, 0, 1);");  //dices
        colors.put(Color.ANSI_BLUE, "-fx-background-color: rgba(0, 160, 225, 1);");
        colors.put(Color.ANSI_RED, "-fx-background-color: rgba(255, 31, 53, 1);");
        colors.put(Color.ANSI_PURPLE, "-fx-background-color: rgba(255, 50, 255, 1);");
        colors.put(Color.ANSI_GREEN, "-fx-background-color: rgba(0, 160, 0, 1);");
        comparator = new HashMap<>();
        comparator.put(Restriction.ONE.escape(), 1);
        comparator.put(Restriction.TWO.escape(), 2);
        comparator.put(Restriction.THREE.escape(), 3);
        comparator.put(Restriction.FOUR.escape(), 4);
        comparator.put(Restriction.FIVE.escape(), 5);
        comparator.put(Restriction.SIX.escape(), 6);
        colorsWindow = new HashMap<>();
        colorsWindow.put(Restriction.ANSI_WHITE.escape(), "-fx-fill: rgba(255, 230, 0, 0);");  //Restrictions
        colorsWindow.put(Restriction.ANSI_RED.escape(), "-fx-fill: rgba(255, 31, 53, 0.5);");
        colorsWindow.put(Restriction.ANSI_GREEN.escape(), "-fx-fill: rgba(0, 160, 0, 0.5);");
        colorsWindow.put(Restriction.ANSI_PURPLE.escape(), "-fx-fill: rgba(255, 50, 255, 0.5);");
        colorsWindow.put(Restriction.ANSI_BLUE.escape(), "-fx-fill: rgba(0, 160, 225, 0.5);");
        colorsWindow.put(Restriction.ANSI_YELLOW.escape(), "-fx-fill: rgba(255, 230, 0, 0.5);");
        cells1 = new ArrayList<>(); cells2 = new ArrayList<>(); cells3 = new ArrayList<>(); cells4 = new ArrayList<>();
        cells1.add(dice11); cells2.add(dice2_11); cells3.add(dice3_11); cells4.add(dice4_11);
        cells1.add(dice12); cells2.add(dice2_12); cells3.add(dice3_12); cells4.add(dice4_12);
        cells1.add(dice13); cells2.add(dice2_13); cells3.add(dice3_13); cells4.add(dice4_13);
        cells1.add(dice14); cells2.add(dice2_14); cells3.add(dice3_14); cells4.add(dice4_14);
        cells1.add(dice15); cells2.add(dice2_15); cells3.add(dice3_15); cells4.add(dice4_15);
        cells1.add(dice21); cells2.add(dice2_21); cells3.add(dice3_21); cells4.add(dice4_21);
        cells1.add(dice22); cells2.add(dice2_22); cells3.add(dice3_22); cells4.add(dice4_22);
        cells1.add(dice23); cells2.add(dice2_23); cells3.add(dice3_23); cells4.add(dice4_23);
        cells1.add(dice24); cells2.add(dice2_24); cells3.add(dice3_24); cells4.add(dice4_24);
        cells1.add(dice25); cells2.add(dice2_25); cells3.add(dice3_25); cells4.add(dice4_25);
        cells1.add(dice31); cells2.add(dice2_31); cells3.add(dice3_31); cells4.add(dice4_31);
        cells1.add(dice32); cells2.add(dice2_32); cells3.add(dice3_32); cells4.add(dice4_32);
        cells1.add(dice33); cells2.add(dice2_33); cells3.add(dice3_33); cells4.add(dice4_33);
        cells1.add(dice34); cells2.add(dice2_34); cells3.add(dice3_34); cells4.add(dice4_34);
        cells1.add(dice35); cells2.add(dice2_35); cells3.add(dice3_35); cells4.add(dice4_35);
        cells1.add(dice41); cells2.add(dice2_41); cells3.add(dice3_41); cells4.add(dice4_41);
        cells1.add(dice42); cells2.add(dice2_42); cells3.add(dice3_42); cells4.add(dice4_42);
        cells1.add(dice43); cells2.add(dice2_43); cells3.add(dice3_43); cells4.add(dice4_43);
        cells1.add(dice44); cells2.add(dice2_44); cells3.add(dice3_44); cells4.add(dice4_44);
        cells1.add(dice45); cells2.add(dice2_45); cells3.add(dice3_45); cells4.add(dice4_45);
        communicator = GUIData.getGUIData().getCommunicator();
        move=false; toolCard=false;
        text.setText("null");
        window2.setVisible(false); username2.setVisible(false);
        window3.setVisible(false); username3.setVisible(false);
        window4.setVisible(false); username4.setVisible(false);
        poolItems = new ArrayList<>();
        window1Items = new ArrayList<>();
        window2Items = new ArrayList<>();
        window3Items = new ArrayList<>();
        window4Items = new ArrayList<>();
        cellsPool = new ArrayList<>();
        cellsPool.add(dice1); cellsPool.add(dice2); cellsPool.add(dice3); cellsPool.add(dice4); cellsPool.add(dice5);
        cellsPool.add(dice6); cellsPool.add(dice7); cellsPool.add(dice8); cellsPool.add(dice9);
    }

    public void editMessage(String message) {
        if(text.getText().equals("null")) text.setText(message);
        else{
            if (message!= null && message.contains("New Turn.")) {
                text.setText(message + "\n");
                toolCard = false;
            }
            else {
                this.text.setText(text.getText().concat(message + "\n"));
                if(message.equals("Command of invalid format.")) {
                    if(!toolCard) move=true;
                }
            }
        }
    }

    public void showPattern(int ID) {
    }

    public void updateTable(Table table) {  //added windowXItems ArrayList in order to refresh correctly all windows
        this.table = table;
        showPUOCs(table.getGamePublicObjectiveCards());
        showTools(table.getGameToolCards());
        showPVOC(table.getPlayer(GUIData.getGUIData().getUsername()).getPrivateObjectiveCard());
        showDraftPool(table.getDraftPool());
        int size = table.getPlayers().size();
        int i=0;
        int j=0;
        for(Player p : table.getPlayers()){
            if(p.getName().equals(GUIData.getGUIData().getUsername())) {
                showWindow(p, window1, cells1, text, window1Items);
            }
            else {
                if(size==2) {showWindow(p, window2, cells2, username2, window2Items);}
                else{
                    if(size==3 && i==0) {showWindow(p, window3, cells3, username3, window3Items); i++;}
                    else{
                        if(size==3 && i==1) showWindow(p, window4, cells4, username4, window4Items);
                        else {
                            if(j==0) {showWindow(p, window2, cells2, username2, window2Items); j++;}
                            if(j==1) {showWindow(p, window3, cells3, username3, window3Items); j++;}
                            if(j==2) {showWindow(p, window4, cells4, username4, window4Items); i++;}
                        }
                    }
                }
            }

        }
    }

    public void showPUOCs(ArrayList<PublicObjectiveCard> cards) {
        Image image;
        int i = 0;
        for (PublicObjectiveCard card : cards) {
            image = new Image(PUOCs.get((Integer) card.getID()));
            switch (i) {
                case (0):
                    publicObj1.setImage(image);
                    break;
                case (1):
                    publicObj2.setImage(image);
                    break;
                case (2):
                    publicObj3.setImage(image);
                    break;
            }
            i++;
        }
    }

    public void showTools(ArrayList<ToolCard> cards) {
        Image image;
        int i = 0;
        for (ToolCard card : cards) {
            image = new Image(tools.get((Integer) card.getID()));
            switch (i) {
                case (0):
                    tool1.setImage(image);
                    break;
                case (1):
                    tool2.setImage(image);
                    break;
                case (2):
                    tool3.setImage(image);
                    break;
            }
            i++;
        }
    }

    public void showPVOC(PrivateObjectiveCard card) {
        Image image;
        image = new Image(PVOCs.get((Integer) card.getID()));
        privateObj.setImage(image);
    }

    public void showDraftPool(ArrayList<Dice> pool) {  //we have to remove all dices before adding new dices
        Platform.runLater(  //Compulsory to update GUI
                () -> {
                    StackPane dice = null;
                    int i = 0;
                    int size = poolItems.size();
                    for(int k=0; k<size; k++){  //reset draftPool
                        draftPool.getChildren().remove(poolItems.get(0));
                        poolItems.remove(0);
                    }
                    for (Dice elem : pool) {
                        try {
                            dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) elem.valueOf())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dice.setStyle(colors.get(elem.getColor()));
                        poolItems.add(dice);
                        draftPool.add(dice, i, 0);
                        cellsPool.get(i).toFront();
                        i++;
                    }
                }
        );
    }
    public void showWindow(Player player, GridPane grid, ArrayList<Rectangle> cells, TextArea username, ArrayList<StackPane> windowItems) {  //we have to remove all dices before adding new dices
        Platform.runLater(  //Compulsory to update GUI
                () -> {
                    grid.setVisible(true);
                    username.setVisible(true);
                    if(!player.getName().equals(GUIData.getGUIData().getUsername()) && !player.getName().equals(username.getText()))username.setText(player.getName());
                    WindowFrame window = player.getWindowFrame();
                    PatternCard pattern = player.getPatternCard();
                    StackPane dice = null;
                    int q=windowItems.size();
                    for(int p=0; p<q; p++){  //reset window
                        if(grid.getChildren().contains(windowItems.get(0))) grid.getChildren().remove(windowItems.get(0));
                        windowItems.remove(0);
                    }
                    int i = 0;
                    for (int j = 1; j < 5; j++) {
                        for (int k = 1; k < 6; k++) {
                            dice = null;
                            if (window.getDice(j, k) != null || pattern.getRestriction(j, k).escape().compareTo("\u2680") >= 0) {  //se la restrizione è un numero oppure c'è un dado nella cella
                                try {
                                    if (window.getDice(j, k) != null) { //se c'è un dado nella cella
                                        dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) window.getDice(j, k).valueOf())));
                                        dice.setStyle(colors.get(window.getDice(j, k).getColor()));
                                    } else  //se la restrizione è un numero
                                        dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) (comparator.get(pattern.getRestriction(j, k).escape())))));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (dice != null) {  //se devo aggiungere alla griglia un dado oppure una restrizione di numero
                                cells.get((j - 1) * (5) + (k - 1)).setStyle(null);
                                windowItems.add(dice);
                                grid.add(dice, k - 1, j - 1);
                                cells.get((j - 1) * (5) + (k - 1)).toFront();
                            } else {  //se devo aggiungere una restrizione di colore
                                cells.get((j - 1) * (5) + (k - 1)).setStyle(null);
                                //cells.get((j - 1) * (5) + (k - 1)).getStyleClass().clear();
                                cells.get((j - 1) * (5) + (k - 1)).setStyle(colorsWindow.get(pattern.getRestriction(j, k).escape()));
                            }
                        }
                        i++;
                    }
                }
        );
    }

    @FXML
    public void selectedTool1(){
        if(toolCard) {
            try {
                GUIData.getGUIData().getCommunicator().sendMessage("0");
                toolCard=false;
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void selectedTool2(){
        if(toolCard) {
            try {
                GUIData.getGUIData().getCommunicator().sendMessage("1");
                toolCard=false;
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void selectedTool3(){
        if(toolCard) {
            try {
                GUIData.getGUIData().getCommunicator().sendMessage("2");
                toolCard=false;
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }
    }
}
