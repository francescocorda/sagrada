package it.polimi.ingsw.client.GUI.table;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.Model.Game.*;
import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.GUI.GUIManager;
import it.polimi.ingsw.client.GUI.login.LoginManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
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
    private ArrayList<StackPane> roundItems = null;  //dices of roundTrack
    private ArrayList<StackPane> window1Items = null; //dices to remove from window1
    private ArrayList<StackPane> window2Items = null; //dices to remove from window2
    private ArrayList<StackPane> window3Items = null; //dices to remove from window3
    private ArrayList<StackPane> window4Items = null; //dices to remove from window4
    private ArrayList<Rectangle> cellsRound = null;  //cells of roundTrack
    private ArrayList<Rectangle> cellsPool = null;  //cells of draftPool
    private Table table = null;
    private Rectangle source = null;
    private int idPool = 0;
    private Communicator communicator;
    private boolean endGame = false;
    private boolean move;
    private boolean toolCard;
    private boolean activeTool = false;
    private boolean roundTrackEnable = false;
    private ArrayList<Circle> signals1;
    private ArrayList<Circle> signals2;
    private ArrayList<Circle> signals3;
    private ArrayList<Circle> signals4;
    int initialPos;
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
    @FXML TextArea username1;
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
    @FXML GridPane selectedDice;
    @FXML TextField operation;
    @FXML Button operationButton;
    @FXML GridPane tableBackground;
    @FXML GridPane cardsBackground;
    @FXML GridPane roundTrackBackground;
    @FXML GridPane roundTrack;
    @FXML Rectangle diceR11; @FXML Rectangle diceR12; @FXML Rectangle diceR13; @FXML Rectangle diceR14;
    @FXML Rectangle diceR15; @FXML Rectangle diceR16; @FXML Rectangle diceR17; @FXML Rectangle diceR18;
    @FXML Rectangle diceR19; @FXML Rectangle diceR21; @FXML Rectangle diceR22; @FXML Rectangle diceR23;
    @FXML Rectangle diceR24; @FXML Rectangle diceR25; @FXML Rectangle diceR26; @FXML Rectangle diceR27;
    @FXML Rectangle diceR28; @FXML Rectangle diceR29; @FXML Rectangle diceR31; @FXML Rectangle diceR32;
    @FXML Rectangle diceR33; @FXML Rectangle diceR34; @FXML Rectangle diceR35; @FXML Rectangle diceR36;
    @FXML Rectangle diceR37; @FXML Rectangle diceR38; @FXML Rectangle diceR39; @FXML Rectangle diceR41;
    @FXML Rectangle diceR42; @FXML Rectangle diceR43; @FXML Rectangle diceR44; @FXML Rectangle diceR45;
    @FXML Rectangle diceR46; @FXML Rectangle diceR47; @FXML Rectangle diceR48; @FXML Rectangle diceR49;
    @FXML Rectangle diceR51; @FXML Rectangle diceR52; @FXML Rectangle diceR53; @FXML Rectangle diceR54;
    @FXML Rectangle diceR55; @FXML Rectangle diceR56; @FXML Rectangle diceR57; @FXML Rectangle diceR58;
    @FXML Rectangle diceR59; @FXML Rectangle diceR61; @FXML Rectangle diceR62; @FXML Rectangle diceR63;
    @FXML Rectangle diceR64; @FXML Rectangle diceR65; @FXML Rectangle diceR66; @FXML Rectangle diceR67;
    @FXML Rectangle diceR68; @FXML Rectangle diceR69; @FXML Rectangle diceR71; @FXML Rectangle diceR72;
    @FXML Rectangle diceR73; @FXML Rectangle diceR74; @FXML Rectangle diceR75; @FXML Rectangle diceR76;
    @FXML Rectangle diceR77; @FXML Rectangle diceR78; @FXML Rectangle diceR79; @FXML Rectangle diceR81;
    @FXML Rectangle diceR82; @FXML Rectangle diceR83; @FXML Rectangle diceR84; @FXML Rectangle diceR85;
    @FXML Rectangle diceR86; @FXML Rectangle diceR87; @FXML Rectangle diceR88; @FXML Rectangle diceR89;
    @FXML Rectangle diceR91; @FXML Rectangle diceR92; @FXML Rectangle diceR93; @FXML Rectangle diceR94;
    @FXML Rectangle diceR95; @FXML Rectangle diceR96; @FXML Rectangle diceR97; @FXML Rectangle diceR98;
    @FXML Rectangle diceR99; @FXML Rectangle diceR101; @FXML Rectangle diceR102; @FXML Rectangle diceR103;
    @FXML Rectangle diceR104; @FXML Rectangle diceR105; @FXML Rectangle diceR106; @FXML Rectangle diceR107;
    @FXML Rectangle diceR108; @FXML Rectangle diceR109;
    @FXML Button quit;
    @FXML Circle signal1; @FXML Circle signal2; @FXML Circle signal3; @FXML Circle signal4; @FXML Circle signal5; @FXML Circle signal6;
    @FXML Circle signal2_1; @FXML Circle signal2_2; @FXML Circle signal2_3; @FXML Circle signal2_4; @FXML Circle signal2_5; @FXML Circle signal2_6;
    @FXML Circle signal3_1; @FXML Circle signal3_2; @FXML Circle signal3_3; @FXML Circle signal3_4; @FXML Circle signal3_5; @FXML Circle signal3_6;
    @FXML Circle signal4_1; @FXML Circle signal4_2; @FXML Circle signal4_3; @FXML Circle signal4_4; @FXML Circle signal4_5; @FXML Circle signal4_6;
    @FXML Text tool1Name; @FXML Text tool1ID; @FXML Text tool1Description; @FXML Text tool1Tokens;
    @FXML Text tool2Name; @FXML Text tool2ID; @FXML Text tool2Description; @FXML Text tool2Tokens;
    @FXML Text tool3Name; @FXML Text tool3ID; @FXML Text tool3Description; @FXML Text tool3Tokens;
    @FXML Text PVOCName; @FXML Text PVOCID; @FXML Text PVOCDescription; @FXML Text PVOCColor;
    @FXML Text POC1Name; @FXML Text POC1ID; @FXML Text POC1Description; @FXML Text POC1Points;
    @FXML Text POC2Name; @FXML Text POC2ID; @FXML Text POC2Description; @FXML Text POC2Points;
    @FXML Text POC3Name; @FXML Text POC3ID; @FXML Text POC3Description; @FXML Text POC3Points;

    public void dragDroppedWindow(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            success = true;
            int row = 7, col = 7;
            source = (Rectangle) event.getTarget();
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
            }
            catch (NetworkErrorException e1) {
                e1.printStackTrace();
            }
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);
        event.consume();
    }

    public void dragOverWindow(DragEvent event){
        if (/*event.getGestureSource() != event.getTarget() &&*/ event.getDragboard().hasString()) {
            /* allow for moving */
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

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
        if(activeTool){
            source = (Rectangle) e.getSource();
            for(int i=0; i<9; i++){
                if (source == cellsPool.get(i)) {
                    idPool = i+1;
                }
            }
            try {
                communicator.sendMessage(""+(idPool-initialPos));
            } catch (NetworkErrorException e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    public void dragDetectedPool(MouseEvent e) {  //IT DOES WORK!
        source = (Rectangle) e.getSource();
        for(int i=0; i<9; i++){
            if (source == cellsPool.get(i)) {
                idPool = i+1;
            }
        }
        SnapshotParameters sp =  new SnapshotParameters();
        sp.setTransform(Transform.scale(1.5, 1.5));
        WritableImage preview = poolItems.get(idPool-initialPos-1).snapshot(sp,null);
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getId());
        db.setContent(content);
        db.setDragView(preview, 45, 45);
        e.consume();
        for(Rectangle r : cells1){
            r.startDragAndDrop(TransferMode.ANY); //accept the dragAndDrop event
        }
        try {
            communicator.sendMessage(""+(idPool-initialPos));
        } catch (NetworkErrorException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    public void selectedDragDetected(MouseEvent event){
        SnapshotParameters sp =  new SnapshotParameters();
        sp.setTransform(Transform.scale(1.5, 1.5));
        WritableImage preview = ((StackPane)selectedDice.getChildren().get(selectedDice.getChildren().size()-1)).snapshot(sp,null);
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(source.getId());
        db.setContent(content);
        db.setDragView(preview, 45, 45);
        event.consume();
    }

    @FXML
    public void mousePressedRound(MouseEvent e) {
        if(roundTrackEnable){
            source = (Rectangle) e.getSource();
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    if (source == cellsRound.get((j) * (10) + (i))) {
                        System.out.println("Selected roundTrack ROUND "+(i+1)+" DICE "+(j+1));
                        try {
                            GUIData.getGUIData().getCommunicator().sendMessage((i+1)+"/"+(j+1));
                        } catch (NetworkErrorException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
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
        colors.put(Color.YELLOW, "-fx-background-color: rgba(255, 230, 0, 1);");  //dices
        colors.put(Color.BLUE, "-fx-background-color: rgba(0, 160, 225, 1);");
        colors.put(Color.RED, "-fx-background-color: rgba(255, 31, 53, 1);");
        colors.put(Color.PURPLE, "-fx-background-color: rgba(255, 50, 255, 1);");
        colors.put(Color.GREEN, "-fx-background-color: rgba(0, 160, 0, 1);");
        comparator = new HashMap<>();
        comparator.put(Restriction.ONE.escape(), 1);
        comparator.put(Restriction.TWO.escape(), 2);
        comparator.put(Restriction.THREE.escape(), 3);
        comparator.put(Restriction.FOUR.escape(), 4);
        comparator.put(Restriction.FIVE.escape(), 5);
        comparator.put(Restriction.SIX.escape(), 6);
        colorsWindow = new HashMap<>();
        //colorsWindow.put(Restriction.WHITE.escape(), "-fx-fill: rgba(255, 230, 0, 0);");  //Restrictions
        colorsWindow.put(Restriction.WHITE.escape(), "-fx-fill: #ffffff;");
        colorsWindow.put(Restriction.RED.escape(), "-fx-fill: #ff6a49;");
        colorsWindow.put(Restriction.GREEN.escape(), "-fx-fill: #82f87e;");
        colorsWindow.put(Restriction.PURPLE.escape(), "-fx-fill: #ee82dc;");
        colorsWindow.put(Restriction.BLUE.escape(), "-fx-fill: #82c0ed;");
        colorsWindow.put(Restriction.YELLOW.escape(), "-fx-fill: #fff486;");
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
        selectedDice.setVisible(false);
        //operation.setVisible(false);
        //operationButton.setVisible(true);
        //tableBackground.getStylesheets().add("GUI/table.css");
        Image backGround = new Image(getClass().getResourceAsStream("/GUI/wood.jpg"));
        tableBackground.setBackground(new Background(new BackgroundImage(backGround, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        cardsBackground.setBackground(new Background(new BackgroundImage(backGround, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        roundTrackBackground.setBackground(new Background(new BackgroundImage(backGround, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        window1.setStyle("-fx-background-color: #FFFFFF;");
        window2.setStyle("-fx-background-color: #FFFFFF;");
        window3.setStyle("-fx-background-color: #FFFFFF;");
        window4.setStyle("-fx-background-color: #FFFFFF;");
        cellsRound = new ArrayList<>();
        roundItems = new ArrayList<>();
        cellsRound.add(diceR11); cellsRound.add(diceR21); cellsRound.add(diceR31); cellsRound.add(diceR41); cellsRound.add(diceR51); cellsRound.add(diceR61); cellsRound.add(diceR71); cellsRound.add(diceR81); cellsRound.add(diceR91); cellsRound.add(diceR101);
        cellsRound.add(diceR12); cellsRound.add(diceR22); cellsRound.add(diceR32); cellsRound.add(diceR42); cellsRound.add(diceR52); cellsRound.add(diceR62); cellsRound.add(diceR72); cellsRound.add(diceR82); cellsRound.add(diceR92); cellsRound.add(diceR102);
        cellsRound.add(diceR13); cellsRound.add(diceR23); cellsRound.add(diceR33); cellsRound.add(diceR43); cellsRound.add(diceR53); cellsRound.add(diceR63); cellsRound.add(diceR73); cellsRound.add(diceR83); cellsRound.add(diceR93); cellsRound.add(diceR103);
        cellsRound.add(diceR14); cellsRound.add(diceR24); cellsRound.add(diceR34); cellsRound.add(diceR44); cellsRound.add(diceR54); cellsRound.add(diceR64); cellsRound.add(diceR74); cellsRound.add(diceR84); cellsRound.add(diceR94); cellsRound.add(diceR104);
        cellsRound.add(diceR15); cellsRound.add(diceR25); cellsRound.add(diceR35); cellsRound.add(diceR45); cellsRound.add(diceR55); cellsRound.add(diceR65); cellsRound.add(diceR75); cellsRound.add(diceR85); cellsRound.add(diceR95); cellsRound.add(diceR105);
        cellsRound.add(diceR16); cellsRound.add(diceR26); cellsRound.add(diceR36); cellsRound.add(diceR46); cellsRound.add(diceR56); cellsRound.add(diceR66); cellsRound.add(diceR76); cellsRound.add(diceR86); cellsRound.add(diceR96); cellsRound.add(diceR106);
        cellsRound.add(diceR17); cellsRound.add(diceR27); cellsRound.add(diceR37); cellsRound.add(diceR47); cellsRound.add(diceR57); cellsRound.add(diceR67); cellsRound.add(diceR77); cellsRound.add(diceR87); cellsRound.add(diceR97); cellsRound.add(diceR107);
        cellsRound.add(diceR18); cellsRound.add(diceR28); cellsRound.add(diceR38); cellsRound.add(diceR48); cellsRound.add(diceR58); cellsRound.add(diceR68); cellsRound.add(diceR78); cellsRound.add(diceR88); cellsRound.add(diceR98); cellsRound.add(diceR108);
        cellsRound.add(diceR19); cellsRound.add(diceR29); cellsRound.add(diceR39); cellsRound.add(diceR49); cellsRound.add(diceR59); cellsRound.add(diceR69); cellsRound.add(diceR79); cellsRound.add(diceR89); cellsRound.add(diceR99); cellsRound.add(diceR109);
        //quit.setDisable(true);
        signals1 = new ArrayList<>();
        signals1.add(signal1); signals1.add(signal2); signals1.add(signal3); signals1.add(signal4); signals1.add(signal5); signals1.add(signal6);
        signals2 = new ArrayList<>();
        signals2.add(signal2_1); signals2.add(signal2_2); signals2.add(signal2_3); signals2.add(signal2_4); signals2.add(signal2_5); signals2.add(signal2_6);
        signals3 = new ArrayList<>();
        signals3.add(signal3_1); signals3.add(signal3_2); signals3.add(signal3_3); signals3.add(signal3_4); signals3.add(signal3_5); signals3.add(signal3_6);
        signals4 = new ArrayList<>();
        signals4.add(signal4_1); signals4.add(signal4_2); signals4.add(signal4_3); signals4.add(signal4_4); signals4.add(signal4_5); signals4.add(signal4_6);
        for(int c=0; c<6; c++){
            signals1.get(c).setVisible(false);
            signals2.get(c).setVisible(false);
            signals3.get(c).setVisible(false);
            signals4.get(c).setVisible(false);
        }
    }

    public void editMessage(String message) {
        if(text.getText().equals("null")) text.setText(message);
        else{
            if(message != null && (message.contains("It's your turn!"))) activeTool = false;
            if (message!= null && (message.contains("New Turn.") || message.contains("New round"))) {
                text.setText(message + "\n");
                toolCard = false;
                if(activeTool = true){
                    makeToolVisible();
                    activeTool = false;
                }
            }
            else {
                this.text.setText(text.getText().concat(message + "\n"));
                if(message.equals("Command of invalid format.")) {
                    if(!toolCard) move=true;
                }
            }
            if (message!= null && message.contains("It's your turn!") && (activeTool == true)){
                tool1.setVisible(true);
                tool2.setVisible(true);
                tool3.setVisible(true);
                activeTool = false;
            }
        }
    }

    public void showPattern(PatternCard pattern) {
    }

    public void updateTable(Table table) {  //added windowXItems ArrayList in order to refresh correctly all windows
        Platform.runLater(  //Compulsory to update GUI
                () -> {
                    this.table = table;
                    showPUOCs(table.getGamePublicObjectiveCards());
                    showTools(table.getGameToolCards());
                    showPVOC(table.getPlayer(GUIData.getGUIData().getUsername()).getPrivateObjectiveCard());
                    showDraftPool(table.getDraftPool());
                    showSelectedDice(table.getActiveDice());
                    showRoundTrack(table.getRoundTrack());
                    int size = table.getPlayers().size();
                    int i=0;
                    int j=0;
                    for(Player p : table.getPlayers()){
                        if(p.getName().equals(GUIData.getGUIData().getUsername())) {
                            showWindow(p, window1, cells1, username1, window1Items, signals1);
                        }
                        else {
                            if(size==2) {showWindow(p, window2, cells2, username2, window2Items, signals2);}
                            else{
                                if(size==3 && i==0) {showWindow(p, window3, cells3, username3, window3Items, signals3); i++;}
                                else{
                                    if(size==3 && i==1) showWindow(p, window4, cells4, username4, window4Items, signals4);
                                    else {
                                        if(j==0) {showWindow(p, window2, cells2, username2, window2Items, signals2); j++;}
                                        else {if(j==1) {showWindow(p, window3, cells3, username3, window3Items, signals3); j++;}
                                        else if(j==2) {showWindow(p, window4, cells4, username4, window4Items, signals4); i++;}}
                                    }
                                }
                            }
                        }

                    }
                }
        );
    }

    public void showSelectedDice(Dice item){
        //Platform.runLater(  //Compulsory to update GUI
              //() -> {
                    if(item != null){
                        selectedDice.getChildren().removeAll();
                        StackPane dice = null;
                        try {
                            dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) item.valueOf())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dice.setStyle(colors.get(item.getColor()));
                        selectedDice.add(dice, 0,0);
                        selectedDice.setVisible(true);
                    }  else selectedDice.setVisible(false);
              //}
        //);
    }

    public void showRoundTrack(RoundTrack RT){
        //Platform.runLater(  //Compulsory to update GUI
                //() -> {
                    Dice elem;
                    StackPane dice = null;
                    int size = roundItems.size();
                    for(int k=0; k<size; k++){  //reset roundTrack
                        roundTrack.getChildren().remove(roundItems.get(0));
                        roundItems.remove(0);
                    }
                    for (int i=0; i<10; i++){
                        ArrayList<Dice> temp = RT.getRoundDices(i);
                        if(temp != null){
                            for(int j=0; j<9; j++){
                                elem = null;
                                if(temp.size()>j) elem = temp.get(j);
                                if(elem != null){  //if there's a dice to add to roundTrack (ROUND i)
                                    try {
                                        dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) elem.valueOf())));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    dice.setStyle(colors.get(elem.getColor()));
                                    roundItems.add(dice);
                                    roundTrack.add(dice, i, j);
                                    cellsRound.get((j) * (10) + (i)).toFront();
                                }
                            }
                        }
                    }
                //}
        //);
    }

    public void showPUOCs(ArrayList<PublicObjectiveCard> cards) {
        //Platform.runLater(  //Compulsory to update GUI
                //() -> {
                    Image image;
                    int i = 0;
                    for (PublicObjectiveCard card : cards) {
                        //image = new Image(PUOCs.get((Integer) card.getID()));
                        image = new Image("/GUI/publicObj.PNG");
                        switch (i) {
                            case (0):
                                POC1Name.setText(card.getName());
                                POC1ID.setText("ID: "+card.getID());
                                POC1Description.setText(card.getDescription());
                                POC1Points.setText(""+card.getPoints());
                                publicObj1.setImage(image);
                                break;
                            case (1):
                                POC2Name.setText(card.getName());
                                POC2ID.setText("ID: "+card.getID());
                                POC2Description.setText(card.getDescription());
                                POC2Points.setText(""+card.getPoints());
                                publicObj2.setImage(image);
                                break;
                            case (2):
                                POC3Name.setText(card.getName());
                                POC3ID.setText("ID: "+card.getID());
                                POC3Description.setText(card.getDescription());
                                POC3Points.setText(""+card.getPoints());
                                publicObj3.setImage(image);
                                break;
                        }
                        i++;
                    }
                //}
        //);

    }

    public void showTools(ArrayList<ToolCard> cards) {
        //Platform.runLater(  //Compulsory to update GUI
                //() -> {
                    Image image;
                    int i = 0;
                    for (ToolCard card : cards) {
                        image = new Image("/GUI/toolCard.PNG");
                        //image = new Image(tools.get((Integer) card.getID()));
                        switch (i) {
                            case (0):
                                tool1Name.setText(card.getName());
                                tool1ID.setText("ID: "+card.getID());
                                String description = card.getDescription();
                                tool1Description.setText(description);
                                tool1Tokens.setText("Tokens: "+card.getNumOfTokens());
                                tool1.setImage(image);
                                break;
                            case (1):
                                tool2Name.setText(card.getName());
                                tool2ID.setText("ID: "+card.getID());
                                description = card.getDescription();
                                tool2Description.setText(description);
                                tool2Tokens.setText("Tokens: "+card.getNumOfTokens());
                                tool2.setImage(image);
                                break;
                            case (2):
                                tool3Name.setText(card.getName());
                                tool3ID.setText("ID: "+card.getID());
                                description = card.getDescription();
                                tool3Description.setText(description);
                                tool3Tokens.setText("Tokens: "+card.getNumOfTokens());
                                tool3.setImage(image);
                                break;
                        }
                        i++;
                    }
                //}
        //);
    }

    public void showPVOC(PrivateObjectiveCard card) {
        //Platform.runLater(  //Compulsory to update GUI
                //() -> {
                    PVOCName.setText(card.getName());
                    PVOCID.setText("ID: "+card.getID());
                    PVOCDescription.setText(card.getDescription());
                    PVOCColor.setText(card.getColor().name());
                    Image image;
                    //image = new Image(PVOCs.get((Integer) card.getID()));
                    image = new Image("/GUI/privateObj.PNG");
                    privateObj.setImage(image);
                //}
        //);
    }

    public void showDraftPool(ArrayList<Dice> pool) {  //we have to remove all dices before adding new dices
        //Platform.runLater(  //Compulsory to update GUI
                //() -> {
                    StackPane dice = null;
                    int i = 0;
                    int size = poolItems.size();
                    for(int k=0; k<size; k++){  //reset draftPool
                        draftPool.getChildren().remove(poolItems.get(0));
                        poolItems.remove(0);
                    }
                    initialPos = ((9-pool.size())/2);
                    i = initialPos;
                    for(Rectangle p : cellsPool){
                        p.setDisable(true);
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
                        cellsPool.get(i).setDisable(false);
                        cellsPool.get(i).toFront();
                        i++;
                    }
                //}
        //);
    }
    public void showWindow(Player player, GridPane grid, ArrayList<Rectangle> cells, TextArea username, ArrayList<StackPane> windowItems, ArrayList<Circle> signals) {  //we have to remove all dices before adding new dices
        //Platform.runLater(  //Compulsory to update GUI
                //() -> {
                    for(int w=0; w<signals.size(); w++) signals.get(w).setVisible(false);
                    int n = player.getNumOfTokens();
                    for(int p=0; p<n; p++){
                        signals.get(p).setVisible(true);
                    }
                    grid.setVisible(true);
                    username.setVisible(true);
                    if(!player.getName().equals(username.getText()))username.setText(player.getName());
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
                                        //dice.setStyle("-fx-background-color: #ffffff;");
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
                //}
        //);
    }

    @FXML
    public void selectedTool1(){
        if(toolCard) {
            try {
                GUIData.getGUIData().getCommunicator().sendMessage("0");
                toolCard=false;
                activeTool = true;
                tool2.setVisible(false);
                tool2Name.setVisible(false);
                tool2ID.setVisible(false);
                tool2Description.setVisible(false);
                tool2Tokens.setVisible(false);
                tool3.setVisible(false);
                tool3Name.setVisible(false);
                tool3ID.setVisible(false);
                tool3Description.setVisible(false);
                tool3Tokens.setVisible(false);
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
                activeTool = true;
                tool1.setVisible(false);
                tool1Name.setVisible(false);
                tool1ID.setVisible(false);
                tool1Description.setVisible(false);
                tool1Tokens.setVisible(false);
                tool3.setVisible(false);
                tool3Name.setVisible(false);
                tool3ID.setVisible(false);
                tool3Description.setVisible(false);
                tool3Tokens.setVisible(false);
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
                activeTool = true;
                tool1.setVisible(false);
                tool1Name.setVisible(false);
                tool1ID.setVisible(false);
                tool1Description.setVisible(false);
                tool1Tokens.setVisible(false);
                tool2.setVisible(false);
                tool2Name.setVisible(false);
                tool2ID.setVisible(false);
                tool2Description.setVisible(false);
                tool2Tokens.setVisible(false);
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void sendOperation(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage(operation.getText());
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void quitAction(MouseEvent event){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("exit");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            URL location = getClass().getResource("/GUI/login.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.centerOnScreen();
            LoginManager LM = fxmlLoader.getController();
            GUIData.getGUIData().getView().setGUIManager(LM);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeToolVisible(){
        tool1.setVisible(true);
        tool1Name.setVisible(true);
        tool1ID.setVisible(true);
        tool1Description.setVisible(true);
        tool1Tokens.setVisible(true);
        tool2.setVisible(true);
        tool2Name.setVisible(true);
        tool2ID.setVisible(true);
        tool2Description.setVisible(true);
        tool2Tokens.setVisible(true);
        tool3.setVisible(true);
        tool3Name.setVisible(true);
        tool3ID.setVisible(true);
        tool3Description.setVisible(true);
        tool3Tokens.setVisible(true);
    }
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard){};
}
