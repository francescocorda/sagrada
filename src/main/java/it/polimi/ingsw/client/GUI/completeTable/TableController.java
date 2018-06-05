package it.polimi.ingsw.client.GUI.completeTable;

import it.polimi.ingsw.client.Client;
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

public class TableController {
    private Rectangle source = null;
    private int idPool = 0;
    private boolean sourceSelectedWindow = false;
    private boolean sourceSelectedPool = false;
    private int sourcerow;
    private int sourcecol;
    private int destrow;
    private int destcol;
    private Image image;
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
            if(sourceSelectedPool){
                text.setText("IDPool: "+idPool+"\ndestination row: "+destrow+"\ndestination col: "+destcol);
                sourceSelectedWindow = false;
                sourceSelectedPool=false;
            } else{
                text.setText("source row: "+sourcerow+"\nsource col: "+sourcecol+"\ndestination row: "+destrow+"\ndestination col: "+destcol);
                sourceSelectedWindow = false;
                sourceSelectedPool=false;
            }
        }
        e.consume();
    }

    @FXML
    public void mousePressedPool(MouseEvent e) {
        if(!sourceSelectedPool && !sourceSelectedWindow){
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
            sourceSelectedPool=true;
        }
    }
    public void initialize() throws IOException {
        StackPane dice;
        text.setEditable(false);
        dice= FXMLLoader.load(Client.class.getResource("/GUI/dice1.fxml"));
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
        privateObj.setImage(image);
    }
}
