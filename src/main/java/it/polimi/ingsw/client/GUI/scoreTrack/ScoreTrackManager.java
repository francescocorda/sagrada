package it.polimi.ingsw.client.GUI.scoreTrack;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.ScoreTrack;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.GUI.GUIManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreTrackManager implements GUIManager {
    private static final int SIZE = 50;
    private static final int MAX_PLAYER = 4;
    @FXML Circle cerchioG1; @FXML Circle cerchioP1;
    @FXML Circle cerchioG2; @FXML Circle cerchioP2;
    @FXML Circle cerchioG3; @FXML Circle cerchioP3;
    @FXML Circle cerchioG4; @FXML Circle cerchioP4;
    @FXML Circle cerchioG5; @FXML Circle cerchioP5;
    @FXML Circle cerchioG6; @FXML Circle cerchioP6;
    @FXML Circle cerchioG7; @FXML Circle cerchioP7;
    @FXML Circle cerchioG8; @FXML Circle cerchioP8;
    @FXML Circle cerchioG9; @FXML Circle cerchioP9;
    @FXML Circle cerchioG10; @FXML Circle cerchioP10;
    @FXML Circle cerchioG11; @FXML Circle cerchioP11;
    @FXML Circle cerchioG12; @FXML Circle cerchioP12;
    @FXML Circle cerchioG13; @FXML Circle cerchioP13;
    @FXML Circle cerchioG14; @FXML Circle cerchioP14;
    @FXML Circle cerchioG15; @FXML Circle cerchioP15;
    @FXML Circle cerchioG16; @FXML Circle cerchioP16;
    @FXML Circle cerchioG17; @FXML Circle cerchioP17;
    @FXML Circle cerchioG18; @FXML Circle cerchioP18;
    @FXML Circle cerchioG19; @FXML Circle cerchioP19;
    @FXML Circle cerchioG20; @FXML Circle cerchioP20;
    @FXML Circle cerchioG21; @FXML Circle cerchioP21;
    @FXML Circle cerchioG22; @FXML Circle cerchioP22;
    @FXML Circle cerchioG23; @FXML Circle cerchioP23;
    @FXML Circle cerchioG24; @FXML Circle cerchioP24;
    @FXML Circle cerchioG25; @FXML Circle cerchioP25;
    @FXML Circle cerchioG26; @FXML Circle cerchioP26;
    @FXML Circle cerchioG27; @FXML Circle cerchioP27;
    @FXML Circle cerchioG28; @FXML Circle cerchioP28;
    @FXML Circle cerchioG29; @FXML Circle cerchioP29;
    @FXML Circle cerchioG30; @FXML Circle cerchioP30;
    @FXML Circle cerchioG31; @FXML Circle cerchioP31;
    @FXML Circle cerchioG32; @FXML Circle cerchioP32;
    @FXML Circle cerchioG33; @FXML Circle cerchioP33;
    @FXML Circle cerchioG34; @FXML Circle cerchioP34;
    @FXML Circle cerchioG35; @FXML Circle cerchioP35;
    @FXML Circle cerchioG36; @FXML Circle cerchioP36;
    @FXML Circle cerchioG37; @FXML Circle cerchioP37;
    @FXML Circle cerchioG38; @FXML Circle cerchioP38;
    @FXML Circle cerchioG39; @FXML Circle cerchioP39;
    @FXML Circle cerchioG40; @FXML Circle cerchioP40;
    @FXML Circle cerchioG41; @FXML Circle cerchioP41;
    @FXML Circle cerchioG42; @FXML Circle cerchioP42;
    @FXML Circle cerchioG43; @FXML Circle cerchioP43;
    @FXML Circle cerchioG44; @FXML Circle cerchioP44;
    @FXML Circle cerchioG45; @FXML Circle cerchioP45;
    @FXML Circle cerchioG46; @FXML Circle cerchioP46;
    @FXML Circle cerchioG47; @FXML Circle cerchioP47;
    @FXML Circle cerchioG48; @FXML Circle cerchioP48;
    @FXML Circle cerchioG49; @FXML Circle cerchioP49;
    @FXML Circle cerchioG50; @FXML Circle cerchioP50;
    @FXML
    Circle cerchioL1;
    @FXML
    Circle cerchioL2;
    @FXML
    Circle cerchioL3;
    @FXML
    Circle cerchioL4;
    @FXML
    Rectangle legend;
    @FXML
    GridPane background;
    @FXML
    Rectangle rectangle;
    @FXML
    Text username1;
    @FXML
    Text username2;
    @FXML
    Text username3;
    @FXML
    Text username4;
    @FXML
    Text score1;
    @FXML
    Text score2;
    @FXML
    Text score3;
    @FXML
    Text score4;

    private ArrayList<Circle> cerchiPiccoli;
    private ArrayList<Circle> cerchiGrandi;
    private ArrayList<Circle> cerchiLegenda;
    private ArrayList<String> colors;
    private ArrayList<Text> usernames;
    private ArrayList<Text> scores;

    public void showScoreTrack(ScoreTrack scoreTrack){
        Platform.runLater(  //Compulsory to update GUI
                () -> {
                    ArrayList<Player> scores = scoreTrack.getScores();
                    int i;
                    for(i = scores.size()-1; i>=0; i--){
                        cerchiLegenda.get(i).setStyle(colors.get(scores.size()-1-i));
                        String name = scoreTrack.getScores().get(i).getName();
                        if(name.length()>10){
                            name = name.substring(0, 9);
                        }
                        usernames.get(i).setText(name);
                        this.scores.get(i).setText(""+scoreTrack.getScores().get(i).getScore());
                        int score = scoreTrack.getScores().get(i).getScore();
                        cerchiPiccoli.get(score == 0 ? 0 : (score-1)%50).setStyle(colors.get(scores.size()-1-i));
                    }
                    i=scores.size();
                    while(i<MAX_PLAYER){
                        cerchiLegenda.get(i).setVisible(false);
                        usernames.get(i).setVisible(false);
                        this.scores.get(i).setVisible(false);
                        i++;
                    }
                }
                );
    }

    public void initialize(){
        GUIData.getGUIData().getView().setGUIManager(this);
        cerchiGrandi = new ArrayList<>();
        cerchiPiccoli = new ArrayList<>();
        cerchiLegenda = new ArrayList<>();
        colors = new ArrayList<>();
        usernames = new ArrayList<>();
        scores = new ArrayList<>();
        cerchiGrandi.add(cerchioG1); cerchiPiccoli.add(cerchioP1);
        cerchiGrandi.add(cerchioG2); cerchiPiccoli.add(cerchioP2);
        cerchiGrandi.add(cerchioG3); cerchiPiccoli.add(cerchioP3);
        cerchiGrandi.add(cerchioG4); cerchiPiccoli.add(cerchioP4);
        cerchiGrandi.add(cerchioG5); cerchiPiccoli.add(cerchioP5);
        cerchiGrandi.add(cerchioG6); cerchiPiccoli.add(cerchioP6);
        cerchiGrandi.add(cerchioG7); cerchiPiccoli.add(cerchioP7);
        cerchiGrandi.add(cerchioG8); cerchiPiccoli.add(cerchioP8);
        cerchiGrandi.add(cerchioG9); cerchiPiccoli.add(cerchioP9);
        cerchiGrandi.add(cerchioG10); cerchiPiccoli.add(cerchioP10);
        cerchiGrandi.add(cerchioG11); cerchiPiccoli.add(cerchioP11);
        cerchiGrandi.add(cerchioG12); cerchiPiccoli.add(cerchioP12);
        cerchiGrandi.add(cerchioG13); cerchiPiccoli.add(cerchioP13);
        cerchiGrandi.add(cerchioG14); cerchiPiccoli.add(cerchioP14);
        cerchiGrandi.add(cerchioG15); cerchiPiccoli.add(cerchioP15);
        cerchiGrandi.add(cerchioG16); cerchiPiccoli.add(cerchioP16);
        cerchiGrandi.add(cerchioG17); cerchiPiccoli.add(cerchioP17);
        cerchiGrandi.add(cerchioG18); cerchiPiccoli.add(cerchioP18);
        cerchiGrandi.add(cerchioG19); cerchiPiccoli.add(cerchioP19);
        cerchiGrandi.add(cerchioG20); cerchiPiccoli.add(cerchioP20);
        cerchiGrandi.add(cerchioG21); cerchiPiccoli.add(cerchioP21);
        cerchiGrandi.add(cerchioG22); cerchiPiccoli.add(cerchioP22);
        cerchiGrandi.add(cerchioG23); cerchiPiccoli.add(cerchioP23);
        cerchiGrandi.add(cerchioG24); cerchiPiccoli.add(cerchioP24);
        cerchiGrandi.add(cerchioG25); cerchiPiccoli.add(cerchioP25);
        cerchiGrandi.add(cerchioG26); cerchiPiccoli.add(cerchioP26);
        cerchiGrandi.add(cerchioG27); cerchiPiccoli.add(cerchioP27);
        cerchiGrandi.add(cerchioG28); cerchiPiccoli.add(cerchioP28);
        cerchiGrandi.add(cerchioG29); cerchiPiccoli.add(cerchioP29);
        cerchiGrandi.add(cerchioG30); cerchiPiccoli.add(cerchioP30);
        cerchiGrandi.add(cerchioG31); cerchiPiccoli.add(cerchioP31);
        cerchiGrandi.add(cerchioG32); cerchiPiccoli.add(cerchioP32);
        cerchiGrandi.add(cerchioG33); cerchiPiccoli.add(cerchioP33);
        cerchiGrandi.add(cerchioG34); cerchiPiccoli.add(cerchioP34);
        cerchiGrandi.add(cerchioG35); cerchiPiccoli.add(cerchioP35);
        cerchiGrandi.add(cerchioG36); cerchiPiccoli.add(cerchioP36);
        cerchiGrandi.add(cerchioG37); cerchiPiccoli.add(cerchioP37);
        cerchiGrandi.add(cerchioG38); cerchiPiccoli.add(cerchioP38);
        cerchiGrandi.add(cerchioG39); cerchiPiccoli.add(cerchioP39);
        cerchiGrandi.add(cerchioG40); cerchiPiccoli.add(cerchioP40);
        cerchiGrandi.add(cerchioG41); cerchiPiccoli.add(cerchioP41);
        cerchiGrandi.add(cerchioG42); cerchiPiccoli.add(cerchioP42);
        cerchiGrandi.add(cerchioG43); cerchiPiccoli.add(cerchioP43);
        cerchiGrandi.add(cerchioG44); cerchiPiccoli.add(cerchioP44);
        cerchiGrandi.add(cerchioG45); cerchiPiccoli.add(cerchioP45);
        cerchiGrandi.add(cerchioG46); cerchiPiccoli.add(cerchioP46);
        cerchiGrandi.add(cerchioG47); cerchiPiccoli.add(cerchioP47);
        cerchiGrandi.add(cerchioG48); cerchiPiccoli.add(cerchioP48);
        cerchiGrandi.add(cerchioG49); cerchiPiccoli.add(cerchioP49);
        cerchiGrandi.add(cerchioG50); cerchiPiccoli.add(cerchioP50);
        cerchiLegenda.add(cerchioL1); cerchiLegenda.add(cerchioL2);
        cerchiLegenda.add(cerchioL3); cerchiLegenda.add(cerchioL4);
        usernames.add(username1); usernames.add(username2); usernames.add(username3); usernames.add(username4);
        scores.add(score1); scores.add(score2); scores.add(score3); scores.add(score4);
        colors.add("-fx-fill: #0000cd;"); colors.add("-fx-fill: #ff0000;");
        colors.add("-fx-fill: #00ff00;"); colors.add("-fx-fill: #ff1493;");
        Image back = new Image(getClass().getResourceAsStream("/GUI/wood.jpg"));
        this.background.setBackground(new Background(new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        rectangle.setStyle("-fx-fill: #ffefd5;");
        for(int i=0; i<SIZE; i++){
            cerchiGrandi.get(i).setStyle("-fx-fill: #696969;");
            cerchiPiccoli.get(i).setStyle("-fx-fill: #f5f5f5;");
        }
        for(int i=0; i<MAX_PLAYER; i++){
            cerchiLegenda.get(i).setStyle("-fx-fill: #ffffff;");
        }
    }

    public void newGameAction(javafx.event.ActionEvent event){
        /*try {
            GUIData.getGUIData().getCommunicator().sendMessage("Game");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }*/
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/lobby.fxml"))));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exitAction(){
        try {
            GUIData.getGUIData().getCommunicator().sendMessage("exit");
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

    @Override
    public void editMessage(String message) {

    }

    @Override
    public void showPattern(PatternCard pattern) {

    }

    @Override
    public void updateTable(Table table) {
        showScoreTrack(table.getScoreTrack());
    }

    @Override
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {

    }
    public void activeElement(String element){}
}
