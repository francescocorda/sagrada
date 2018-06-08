package it.polimi.ingsw.client.GUI.table;

import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.Model.Game.Dice;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.GUI.GUIManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private HashMap<Integer, String> PUOCs = null;
    private HashMap<Integer, String> tools = null;
    private HashMap<Integer, String> PVOCs = null;
    private HashMap<Integer, String> dices = null;
    private HashMap<Color, String> colors = null;
    private Table table = null;
    private Rectangle source = null;
    private int idPool = 0;
    private boolean sourceSelectedWindow = false;
    private boolean sourceSelectedPool = false;
    private int sourcerow;
    private int sourcecol;
    private int destrow;
    private int destcol;
    boolean endGame = false;
    String message;
    @FXML
    GridPane draftPool;
    @FXML
    Rectangle dice1;
    @FXML
    Rectangle dice2;
    @FXML
    Rectangle dice3;
    @FXML
    Rectangle dice4;
    @FXML
    Rectangle dice5;
    @FXML
    Rectangle dice6;
    @FXML
    Rectangle dice7;
    @FXML
    Rectangle dice8;
    @FXML
    Rectangle dice9;
    //window1
    @FXML
    GridPane window1;
    @FXML
    Rectangle dice11;
    @FXML
    Rectangle dice12;
    @FXML
    Rectangle dice13;
    @FXML
    Rectangle dice14;
    @FXML
    Rectangle dice15;
    @FXML
    Rectangle dice21;
    @FXML
    Rectangle dice22;
    @FXML
    Rectangle dice23;
    @FXML
    Rectangle dice24;
    @FXML
    Rectangle dice25;
    @FXML
    Rectangle dice31;
    @FXML
    Rectangle dice32;
    @FXML
    Rectangle dice33;
    @FXML
    Rectangle dice34;
    @FXML
    Rectangle dice35;
    @FXML
    Rectangle dice41;
    @FXML
    Rectangle dice42;
    @FXML
    Rectangle dice43;
    @FXML
    Rectangle dice44;
    @FXML
    Rectangle dice45;
    //window2
    @FXML
    GridPane window2;
    @FXML
    TextArea username2;
    @FXML
    Rectangle dice2_11;
    @FXML
    Rectangle dice2_12;
    @FXML
    Rectangle dice2_13;
    @FXML
    Rectangle dice2_14;
    @FXML
    Rectangle dice2_15;
    @FXML
    Rectangle dice2_21;
    @FXML
    Rectangle dice2_22;
    @FXML
    Rectangle dice2_23;
    @FXML
    Rectangle dice2_24;
    @FXML
    Rectangle dice2_25;
    @FXML
    Rectangle dice2_31;
    @FXML
    Rectangle dice2_32;
    @FXML
    Rectangle dice2_33;
    @FXML
    Rectangle dice2_34;
    @FXML
    Rectangle dice2_35;
    @FXML
    Rectangle dice2_41;
    @FXML
    Rectangle dice2_42;
    @FXML
    Rectangle dice2_43;
    @FXML
    Rectangle dice2_44;
    @FXML
    Rectangle dice2_45;
    //window2
    @FXML
    GridPane window3;
    @FXML
    TextArea username3;
    @FXML
    Rectangle dice3_11;
    @FXML
    Rectangle dice3_12;
    @FXML
    Rectangle dice3_13;
    @FXML
    Rectangle dice3_14;
    @FXML
    Rectangle dice3_15;
    @FXML
    Rectangle dice3_21;
    @FXML
    Rectangle dice3_22;
    @FXML
    Rectangle dice3_23;
    @FXML
    Rectangle dice3_24;
    @FXML
    Rectangle dice3_25;
    @FXML
    Rectangle dice3_31;
    @FXML
    Rectangle dice3_32;
    @FXML
    Rectangle dice3_33;
    @FXML
    Rectangle dice3_34;
    @FXML
    Rectangle dice3_35;
    @FXML
    Rectangle dice3_41;
    @FXML
    Rectangle dice3_42;
    @FXML
    Rectangle dice3_43;
    @FXML
    Rectangle dice3_44;
    @FXML
    Rectangle dice3_45;
    //window2
    @FXML
    GridPane window4;
    @FXML
    TextArea username4;
    @FXML
    Rectangle dice4_11;
    @FXML
    Rectangle dice4_12;
    @FXML
    Rectangle dice4_13;
    @FXML
    Rectangle dice4_14;
    @FXML
    Rectangle dice4_15;
    @FXML
    Rectangle dice4_21;
    @FXML
    Rectangle dice4_22;
    @FXML
    Rectangle dice4_23;
    @FXML
    Rectangle dice4_24;
    @FXML
    Rectangle dice4_25;
    @FXML
    Rectangle dice4_31;
    @FXML
    Rectangle dice4_32;
    @FXML
    Rectangle dice4_33;
    @FXML
    Rectangle dice4_34;
    @FXML
    Rectangle dice4_35;
    @FXML
    Rectangle dice4_41;
    @FXML
    Rectangle dice4_42;
    @FXML
    Rectangle dice4_43;
    @FXML
    Rectangle dice4_44;
    @FXML
    Rectangle dice4_45;
    @FXML
    TextArea text;

    //Cards
    @FXML
    ImageView tool1;
    @FXML
    ImageView tool2;
    @FXML
    ImageView tool3;
    @FXML
    ImageView publicObj1;
    @FXML
    ImageView publicObj2;
    @FXML
    ImageView publicObj3;
    @FXML
    ImageView privateObj;

    @FXML
    public void mousePressedWindow(MouseEvent e) {
        int row = 7, col = 7;
        source = (Rectangle) e.getSource();
        if (source.equals(dice11)) {
            row = 1;
            col = 1;
        }
        if (source.equals(dice12)) {
            row = 1;
            col = 2;
        }
        if (source.equals(dice13)) {
            row = 1;
            col = 3;
        }
        if (source.equals(dice14)) {
            row = 1;
            col = 4;
        }
        if (source.equals(dice15)) {
            row = 1;
            col = 5;
        }
        if (source.equals(dice21)) {
            row = 2;
            col = 1;
        }
        if (source.equals(dice22)) {
            row = 2;
            col = 2;
        }
        if (source.equals(dice23)) {
            row = 2;
            col = 3;
        }
        if (source.equals(dice24)) {
            row = 2;
            col = 4;
        }
        if (source.equals(dice25)) {
            row = 2;
            col = 5;
        }
        if (source.equals(dice31)) {
            row = 3;
            col = 1;
        }
        if (source.equals(dice32)) {
            row = 3;
            col = 2;
        }
        if (source.equals(dice33)) {
            row = 3;
            col = 3;
        }
        if (source.equals(dice34)) {
            row = 3;
            col = 4;
        }
        if (source.equals(dice35)) {
            row = 3;
            col = 5;
        }
        if (source.equals(dice41)) {
            row = 4;
            col = 1;
        }
        if (source.equals(dice42)) {
            row = 4;
            col = 2;
        }
        if (source.equals(dice43)) {
            row = 4;
            col = 3;
        }
        if (source.equals(dice44)) {
            row = 4;
            col = 4;
        }
        if (source.equals(dice45)) {
            row = 4;
            col = 5;
        }
        if (!sourceSelectedWindow && !sourceSelectedPool) {
            sourcerow = row;
            sourcecol = col;
            sourceSelectedWindow = true;
        } else {
            destrow = row;
            destcol = col;
            if (sourceSelectedPool) {
                text.setText("IDPool: " + idPool + "\ndestination row: " + destrow + "\ndestination col: " + destcol);
                //server.update(message);
                sourceSelectedWindow = false;
                sourceSelectedPool = false;
            } else {
                text.setText("source row: " + sourcerow + "\nsource col: " + sourcecol + "\ndestination row: " + destrow + "\ndestination col: " + destcol);
                sourceSelectedWindow = false;
                sourceSelectedPool = false;
            }
        }
        e.consume();
    }

    @FXML
    public void mousePressedPool(MouseEvent e) {
        if (!sourceSelectedPool && !sourceSelectedWindow) {
            source = (Rectangle) e.getSource();
            if (source == dice1) {
                idPool = 1;
            }
            if (source == dice2) {
                idPool = 2;
            }
            if (source == dice3) {
                idPool = 3;
            }
            if (source == dice4) {
                idPool = 4;
            }
            if (source == dice5) {
                idPool = 5;
            }
            if (source == dice6) {
                idPool = 6;
            }
            if (source == dice7) {
                idPool = 7;
            }
            if (source == dice8) {
                idPool = 8;
            }
            if (source == dice9) {
                idPool = 9;
            }
            sourceSelectedPool = true;
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
        colors.put(Color.ANSI_YELLOW, "-fx-background-color: rgba(255, 230, 0, 1);");
        colors.put(Color.ANSI_BLUE, "-fx-background-color: rgba(0, 160, 225, 1);");
        colors.put(Color.ANSI_RED, "-fx-background-color: rgba(255, 31, 53, 1);");
        colors.put(Color.ANSI_PURPLE, "-fx-background-color: rgba(255, 50, 255, 1);");
        colors.put(Color.ANSI_GREEN, "-fx-background-color: rgba(0, 160, 0, 1);");

        /*dice= FXMLLoader.load(Client.class.getResource("/GUI/dice1.fxml"));
        dice.setStyle("-fx-background-color: rgba(255, 230, 0, 1);");  //yellow
        draftPool.add(dice, 0,0);
        dice1.toFront();
        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice2.fxml"));
        dice.setStyle("-fx-background-color: rgba(255, 31, 53, 1);");  //red
        draftPool.add(dice, 1,0);
        dice2.toFront();
        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice3.fxml"));
        dice.setStyle("-fx-background-color: rgba(0, 160, 0, 1);");    //green
        draftPool.add(dice, 2,0);
        dice.toFront();
        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice4.fxml"));
        dice.setStyle("-fx-background-color: rgba(255, 50, 255, 1);");  //purple
        draftPool.add(dice, 3,0);
        dice4.toFront();
        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice6.fxml"));
        dice.setStyle("-fx-background-color: rgba(0, 160, 225, 1);");  //blue
        draftPool.add(dice, 4,0);
        dice5.toFront();

        //prove window
        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice4.fxml"));
        dice.setStyle("-fx-background-color: rgba(255, 31, 53, 1);");  //red
        window1.add(dice, 4, 3);
        dice45.toFront();

        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice2.fxml"));
        dice.setStyle("-fx-background-color: rgba(255, 230, 0, 1);");  //yellow
        window1.add(dice, 1, 1);
        dice22.toFront();

        //prova restrizione numero
        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice3.fxml"));
        window1.add(dice, 2, 1);
        dice23.toFront();

        //prova restrizione colore
        dice34.setStyle("-fx-fill: rgba(255, 31, 53, 0.4);");
        dice35.setStyle("-fx-fill: rgba(255, 230, 0, 0.4);");  //yellow

        //inserisco carte
        image = new Image(Client.class.getResourceAsStream("/GUI/tool1.PNG"));
        tool1.setImage(image);
        tool2.setImage(image);
        tool3.setImage(image);
        image = new Image(Client.class.getResourceAsStream("/GUI/publicObj1.PNG"));
        publicObj1.setImage(image);
        publicObj2.setImage(image);
        publicObj3.setImage(image);
        image = new Image(Client.class.getResourceAsStream("/GUI/privateObj.PNG"));
        privateObj.setImage(image);*/
    }

    public void notify(String message) {

    }

    public void editMessage(String message) {
        this.message = message;
    }

    public void showPattern(int ID) {
    }

    public void updateTable(Table table) {
        this.table = table;
        showPUOCs(table.getGamePublicObjectiveCards());
        showTools(table.getGameToolCards());
        showPVOC(table.getPlayer(GUIData.getGUIData().getUsername()).getPrivateObjectiveCard());
        showDraftPool(table.getDraftPool());
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
                    for (Dice elem : pool) {
                        try {
                            dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) elem.valueOf())));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        switch (i) {
                            case (0):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 0, 0);
                                dice1.toFront();
                                break;
                            case (1):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 1, 0);
                                dice2.toFront();
                                break;
                            case (2):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 2, 0);
                                dice3.toFront();
                                break;
                            case (3):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 3, 0);
                                dice4.toFront();
                                break;
                            case (4):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 4, 0);
                                dice5.toFront();
                                break;
                            case (5):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 5, 0);
                                dice6.toFront();
                                break;
                            case (6):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 6, 0);
                                dice7.toFront();
                                break;
                            case (7):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 7, 0);
                                dice8.toFront();
                                break;
                            case (8):
                                dice.setStyle(colors.get(elem.getColor()));
                                draftPool.add(dice, 8, 0);
                                dice9.toFront();
                                break;
                        }
                        i++;
                    }
                }
        );
    }
}
