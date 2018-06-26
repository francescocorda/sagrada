package it.polimi.ingsw.client.GUI.lobby;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.Patterns.Restriction;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Color;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.GUI.GUIManager;
import it.polimi.ingsw.client.GUI.table.TableManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

public class LobbyManager implements GUIManager{
    private HashMap<String, Integer> comparator = null;
    private Image image;
    private ImageView source;
    private Integer count;
    private HashMap<Integer, ImageView> HM;
    private Table table;
    private String temp;
    private boolean flag=false;
    private HashMap<Integer, String> PVOCs = null;  //Private Objective Cards
    private String src;
    private Event e = null;
    private HashMap<Integer, String> dices = null;
    @FXML
    ImageView card1;
    @FXML
    ImageView card2;
    @FXML
    ImageView card3;
    @FXML
    ImageView card4;
    @FXML
    TextArea message;
    @FXML
    DatePicker date;
    @FXML
    Button joinLobby;
    @FXML
    ImageView privateObj;
    @FXML AnchorPane background;
    @FXML Text phrase;
    @FXML Text PVOCName; @FXML Text PVOCID; @FXML Text PVOCDescription; @FXML Text PVOCColor;
    @FXML GridPane pattern1;
    @FXML Text pattern1Name;
    @FXML Rectangle cell1_11; @FXML Rectangle cell1_12; @FXML Rectangle cell1_13; @FXML Rectangle cell1_14; @FXML Rectangle cell1_15;
    @FXML Rectangle cell1_21; @FXML Rectangle cell1_22; @FXML Rectangle cell1_23; @FXML Rectangle cell1_24; @FXML Rectangle cell1_25;
    @FXML Rectangle cell1_31; @FXML Rectangle cell1_32; @FXML Rectangle cell1_33; @FXML Rectangle cell1_34; @FXML Rectangle cell1_35;
    @FXML Rectangle cell1_41; @FXML Rectangle cell1_42; @FXML Rectangle cell1_43; @FXML Rectangle cell1_44; @FXML Rectangle cell1_45;
    private ArrayList<Rectangle> pattern1Items;
    @FXML GridPane pattern2;
    @FXML Text pattern2Name;
    @FXML Rectangle cell2_11; @FXML Rectangle cell2_12; @FXML Rectangle cell2_13; @FXML Rectangle cell2_14; @FXML Rectangle cell2_15;
    @FXML Rectangle cell2_21; @FXML Rectangle cell2_22; @FXML Rectangle cell2_23; @FXML Rectangle cell2_24; @FXML Rectangle cell2_25;
    @FXML Rectangle cell2_31; @FXML Rectangle cell2_32; @FXML Rectangle cell2_33; @FXML Rectangle cell2_34; @FXML Rectangle cell2_35;
    @FXML Rectangle cell2_41; @FXML Rectangle cell2_42; @FXML Rectangle cell2_43; @FXML Rectangle cell2_44; @FXML Rectangle cell2_45;
    private ArrayList<Rectangle> pattern2Items;
    @FXML GridPane pattern3;
    @FXML Text pattern3Name;
    @FXML Rectangle cell3_11; @FXML Rectangle cell3_12; @FXML Rectangle cell3_13; @FXML Rectangle cell3_14; @FXML Rectangle cell3_15;
    @FXML Rectangle cell3_21; @FXML Rectangle cell3_22; @FXML Rectangle cell3_23; @FXML Rectangle cell3_24; @FXML Rectangle cell3_25;
    @FXML Rectangle cell3_31; @FXML Rectangle cell3_32; @FXML Rectangle cell3_33; @FXML Rectangle cell3_34; @FXML Rectangle cell3_35;
    @FXML Rectangle cell3_41; @FXML Rectangle cell3_42; @FXML Rectangle cell3_43; @FXML Rectangle cell3_44; @FXML Rectangle cell3_45;
    private ArrayList<Rectangle> pattern3Items;
    @FXML GridPane pattern4;
    @FXML Text pattern4Name;
    @FXML Rectangle cell4_11; @FXML Rectangle cell4_12; @FXML Rectangle cell4_13; @FXML Rectangle cell4_14; @FXML Rectangle cell4_15;
    @FXML Rectangle cell4_21; @FXML Rectangle cell4_22; @FXML Rectangle cell4_23; @FXML Rectangle cell4_24; @FXML Rectangle cell4_25;
    @FXML Rectangle cell4_31; @FXML Rectangle cell4_32; @FXML Rectangle cell4_33; @FXML Rectangle cell4_34; @FXML Rectangle cell4_35;
    @FXML Rectangle cell4_41; @FXML Rectangle cell4_42; @FXML Rectangle cell4_43; @FXML Rectangle cell4_44; @FXML Rectangle cell4_45;
    private ArrayList<Rectangle> pattern4Items;
    private HashMap<String, String> colors = null;  //Restriction of colors

    public void joinLobby(javafx.event.ActionEvent event){
        try{
            this.e = event;
            LocalDate isoDate = date.getValue();
            ChronoLocalDate chronoLocalDate = ((isoDate != null) ? date.getChronology().date(isoDate) : null);
            Communicator communicator = GUIData.getGUIData().getCommunicator();
            String username = GUIData.getGUIData().getUsername();
            try {
                long time = chronoLocalDate.toEpochDay()*24*60*60;
                time = isDateValid(time);
                communicator.lobby(username, time);
                date.setVisible(false);
                joinLobby.setVisible(false);
                phrase.setVisible(false);
            } catch (NetworkErrorException e) {
                this.message.setText("Network Error! Server may be DOWN!\n");
            } catch (NotValidInputException | NullPointerException e) {
                this.message.setText("Not valid Date! Retry!\n");
            }
        } catch (Exception exc){
            System.out.println("IT'S E BIATCH:\n "+ exc);
        }
    }
    @FXML
    public void initialize() {
        GUIData.getGUIData().getView().setGUIManager(this);
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        PVOCName.setVisible(false);
        PVOCID.setVisible(false);
        PVOCDescription.setVisible(false);
        PVOCColor.setVisible(false);
        HM = new HashMap<>();
        HM.put(0, card1);
        HM.put(1, card2);
        HM.put(2, card3);
        HM.put(3, card4);
        count = 0;
        PVOCs = new HashMap<Integer, String>();
        src = "/GUI/privateObj1.PNG";
        PVOCs.put(1, src);
        src = "/GUI/privateObj2.PNG";
        PVOCs.put(2, src);
        src = "/GUI/privateObj3.PNG";
        PVOCs.put(3, src);
        src = "/GUI/privateObj4.PNG";
        PVOCs.put(4, src);
        src = "/GUI/privateObj5.PNG";
        PVOCs.put(5, src);
        Image back = new Image(getClass().getResourceAsStream("/GUI/wood.jpg"));
        this.background.setBackground(new Background(new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        pattern1Items = new ArrayList<>();
        pattern2Items = new ArrayList<>();
        pattern3Items = new ArrayList<>();
        pattern4Items = new ArrayList<>();
        pattern1Items.add(cell1_11);pattern1Items.add(cell1_12);pattern1Items.add(cell1_13);pattern1Items.add(cell1_14);pattern1Items.add(cell1_15);
        pattern1Items.add(cell1_21);pattern1Items.add(cell1_22);pattern1Items.add(cell1_23);pattern1Items.add(cell1_24);pattern1Items.add(cell1_25);
        pattern1Items.add(cell1_31);pattern1Items.add(cell1_32);pattern1Items.add(cell1_33);pattern1Items.add(cell1_34);pattern1Items.add(cell1_35);
        pattern1Items.add(cell1_41);pattern1Items.add(cell1_42);pattern1Items.add(cell1_43);pattern1Items.add(cell1_44);pattern1Items.add(cell1_45);
        pattern2Items.add(cell2_11);pattern2Items.add(cell2_12);pattern2Items.add(cell2_13);pattern2Items.add(cell2_14);pattern2Items.add(cell2_15);
        pattern2Items.add(cell2_21);pattern2Items.add(cell2_22);pattern2Items.add(cell2_23);pattern2Items.add(cell2_24);pattern2Items.add(cell2_25);
        pattern2Items.add(cell2_31);pattern2Items.add(cell2_32);pattern2Items.add(cell2_33);pattern2Items.add(cell2_34);pattern2Items.add(cell2_35);
        pattern2Items.add(cell2_41);pattern2Items.add(cell2_42);pattern2Items.add(cell2_43);pattern2Items.add(cell2_44);pattern2Items.add(cell2_45);
        pattern3Items.add(cell3_11);pattern3Items.add(cell3_12);pattern3Items.add(cell3_13);pattern3Items.add(cell3_14);pattern3Items.add(cell3_15);
        pattern3Items.add(cell3_21);pattern3Items.add(cell3_22);pattern3Items.add(cell3_23);pattern3Items.add(cell3_24);pattern3Items.add(cell3_25);
        pattern3Items.add(cell3_31);pattern3Items.add(cell3_32);pattern3Items.add(cell3_33);pattern3Items.add(cell3_34);pattern3Items.add(cell3_35);
        pattern3Items.add(cell3_41);pattern3Items.add(cell3_42);pattern3Items.add(cell3_43);pattern3Items.add(cell3_44);pattern3Items.add(cell3_45);
        pattern4Items.add(cell4_11);pattern4Items.add(cell4_12);pattern4Items.add(cell4_13);pattern4Items.add(cell4_14);pattern4Items.add(cell4_15);
        pattern4Items.add(cell4_21);pattern4Items.add(cell4_22);pattern4Items.add(cell4_23);pattern4Items.add(cell4_24);pattern4Items.add(cell4_25);
        pattern4Items.add(cell4_31);pattern4Items.add(cell4_32);pattern4Items.add(cell4_33);pattern4Items.add(cell4_34);pattern4Items.add(cell4_35);
        pattern4Items.add(cell4_41);pattern4Items.add(cell4_42);pattern4Items.add(cell4_43);pattern4Items.add(cell4_44);pattern4Items.add(cell4_45);
        colors = new HashMap<>();  //restrictions
        colors.put(Restriction.ANSI_WHITE.escape(), "-fx-fill: #ffffff;");
        colors.put(Restriction.ANSI_RED.escape(), "-fx-fill: #ff6a49;");
        colors.put(Restriction.ANSI_GREEN.escape(), "-fx-fill: #82f87e;");
        colors.put(Restriction.ANSI_PURPLE.escape(), "-fx-fill: #ee82dc;");
        colors.put(Restriction.ANSI_BLUE.escape(), "-fx-fill: #82c0ed;");
        colors.put(Restriction.ANSI_YELLOW.escape(), "-fx-fill: #fff486;");
        dices = new HashMap<Integer, String>();
        String image = "/GUI/dice1_small.fxml";
        dices.put(1, image);
        image = "/GUI/dice2_small.fxml";
        dices.put(2, image);
        image = "/GUI/dice3_small.fxml";
        dices.put(3, image);
        image = "/GUI/dice4_small.fxml";
        dices.put(4, image);
        image = "/GUI/dice5_small.fxml";
        dices.put(5, image);
        image = "/GUI/dice6_small.fxml";
        dices.put(6, image);
        comparator = new HashMap<>();
        comparator.put(Restriction.ONE.escape(), 1);
        comparator.put(Restriction.TWO.escape(), 2);
        comparator.put(Restriction.THREE.escape(), 3);
        comparator.put(Restriction.FOUR.escape(), 4);
        comparator.put(Restriction.FIVE.escape(), 5);
        comparator.put(Restriction.SIX.escape(), 6);
        pattern1.setVisible(false);
        pattern2.setVisible(false);
        pattern3.setVisible(false);
        pattern4.setVisible(false);
        pattern1Name.setVisible(false);
        pattern2Name.setVisible(false);
        pattern3Name.setVisible(false);
        pattern4Name.setVisible(false);
    }

    @FXML
    public void mousePressed(MouseEvent event){
        String card=null;
        source = (ImageView) event.getSource();
        if (source == card1) {
            card = "0";
        }
        if (source == card2) {
            card = "1";
        }
        if (source == card3) {  //to modify
            card = "2";
        }
        if (source == card4) {  //to modify
            card = "3";
        }
        try {
            GUIData.getGUIData().getCommunicator().sendMessage(card);
        } catch (NetworkErrorException e) {
            this.message.setText("Network Error! Server may be DOWN!\n");
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            URL location = getClass().getResource("/GUI/table.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setMaximized(true);
            TableManager TM = fxmlLoader.getController();
            TM.editMessage(temp);
            if(table!=null) TM.updateTable(table);
        } catch (IOException e) {
            this.message.setText("GUI ERROR!\n");
            e.printStackTrace();
        }
    }

    public void patternChoose(MouseEvent event){
        String card=null;
        GridPane source = new GridPane();
        source = (GridPane) event.getSource();
        if (source == pattern1) {
            card = "0";
        }
        if (source == pattern2) {
            card = "1";
        }
        if (source == pattern3) {  //to modify
            card = "2";
        }
        if (source == pattern4) {  //to modify
            card = "3";
        }
        try {
            GUIData.getGUIData().getCommunicator().sendMessage(card);
        } catch (NetworkErrorException e) {
            this.message.setText("Network Error! Server may be DOWN!\n");
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            URL location = getClass().getResource("/GUI/table.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(location);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setMaximized(true);
            TableManager TM = fxmlLoader.getController();
            TM.editMessage(temp);
            if(table!=null) TM.updateTable(table);
        } catch (IOException e) {
            this.message.setText("GUI ERROR!\n");
            e.printStackTrace();
        }
    }

    public void editMessage(String message){
        this.message.setText(this.message.getText().concat(message.concat("\n")));
        if(message.equals("back_to_game")){
            Platform.runLater(  //Compulsory to update GUI
                    () -> {
                        temp = message;
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        URL location = getClass().getResource("/GUI/table.fxml");
                        FXMLLoader fxmlLoader = new FXMLLoader(location);
                        try {
                            stage.setScene(new Scene(fxmlLoader.load()));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        stage.setMaximized(true);
                        TableManager TM = fxmlLoader.getController();
                        TM.editMessage(temp);
                        if(table!=null) TM.updateTable(table);
                    }
            );
        }
        else if(message.equals("Pattern card assigned.")) {
            temp = new String();
            temp = message+"\n";
            flag = true;
        } else if(flag){
            temp = temp.concat(message+"\n");
        }
    }

    public void showPattern(PatternCard pattern){
        Platform.runLater(  //Compulsory to update GUI
                () -> {
                    GridPane grid= new GridPane();
                    ArrayList<Rectangle> items = new ArrayList<>();
                    Text name = new Text();
                    switch(count){
                        case(0):
                            grid = pattern1;
                            items = pattern1Items;
                            name = pattern1Name;
                            break;
                        case(1):
                            grid = pattern2;
                            items = pattern2Items;
                            name = pattern2Name;
                            break;
                        case(2):
                            grid = pattern3;
                            items = pattern3Items;
                            name = pattern3Name;
                            break;
                        case(3):
                            grid = pattern4;
                            items = pattern4Items;
                            name = pattern4Name;
                            break;
                    }
                    name.setText(pattern.getName());
                    grid.setVisible(true);
                    name.setVisible(true);
                    StackPane dice = new StackPane();
                    for(int i=1; i<5; i++){
                        for(int j=1; j<6; j++){
                            dice = null;
                            if (pattern.getRestriction(i, j).escape().compareTo("\u2680") >= 0) {
                                try {
                                    dice = FXMLLoader.load(getClass().getResource(dices.get((Integer) (comparator.get(pattern.getRestriction(i, j).escape())))));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            if (dice != null) {
                                dice.setStyle("-fx-background-color: #ffffff;");
                                grid.add(dice, j - 1, i - 1);
                            } else{
                                items.get((i - 1) * (5) + (j - 1)).setStyle(colors.get(pattern.getRestriction(i, j).escape()));
                            }
                            }
                    }
                    count++;
                }
                );

        /*if(HM==null) System.out.println("HM null");
        else {
            if (HM.get(0) == null) System.out.println("0 null");
            if (HM.get(1) == null) System.out.println("1 null");
            if (HM.get(2) == null) System.out.println("2 null");
            if (HM.get(3) == null) System.out.println("3 null");
        }
        switch (ID) {
            case(1):
                setImage("/GUI/pattern1.PNG");
                break;
            case(2):
                setImage("/GUI/pattern2.PNG");
                break;
            case(3):
                setImage("/GUI/pattern3.PNG");
                break;
            case(4):
                setImage("/GUI/pattern4.PNG");
                break;
            case(5):
                setImage("/GUI/pattern5.PNG");
                break;
            case(6):
                setImage("/GUI/pattern6.PNG");
                break;
            case(7):
                setImage("/GUI/pattern7.PNG");
                break;
            case(8):
                setImage("/GUI/pattern8.PNG");
                break;
            case(9):
                setImage("/GUI/pattern9.PNG");
                break;
            case(10):
                setImage("/GUI/pattern10.PNG");
                break;
            case(11):
                setImage("/GUI/pattern11.PNG");
                break;
            case(12):
                setImage("/GUI/pattern12.PNG");
                break;
            case(13):
                setImage("/GUI/pattern13.PNG");
                break;
            case(14):
                setImage("/GUI/pattern14.PNG");
                break;
            case(15):
                setImage("/GUI/pattern15.PNG");
                break;
            case(16):
                setImage("/GUI/pattern16.PNG");
                break;
            case(17):
                setImage("/GUI/pattern17.PNG");
                break;
            case(18):
                setImage("/GUI/pattern18.PNG");
                break;
            case(19):
                setImage("/GUI/pattern19.PNG");
                break;
            case(20):
                setImage("/GUI/pattern20.PNG");
                break;
            case(21):
                setImage("/GUI/pattern21.PNG");
                break;
            case(22):
                setImage("/GUI/pattern22.PNG");
                break;
            case(23):
                setImage("/GUI/pattern23.PNG");
                break;
            case(24):
                setImage("/GUI/pattern24.PNG");
                break;
            default:
                break;
        }*/
    }

    /*private void setImage(String path){
        image = new Image(Client.class.getResourceAsStream(path));
        HM.get(count).setImage(image);
        HM.get(count).setVisible(true);
        count++;
    }*/
    public void updateTable(Table table){
        this.table=table;
    }

    private long isDateValid (long timeUnix) throws NotValidInputException{
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(timeUnix*1000L);
        int year = cal.get(GregorianCalendar.YEAR);
        int month = cal.get(GregorianCalendar.MONTH);
        int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
        cal = new GregorianCalendar(year, month, day);
        cal.setLenient(false);
        try {
            cal.get(Calendar.DATE);
            return cal.getTime().getTime()/1000;
        } catch (IllegalArgumentException e) {
            throw new NotValidInputException();
        }
    }
    public void displayPrivateObjectiveCard(PrivateObjectiveCard card) {
        PVOCName.setText(card.getName());
        PVOCName.setVisible(true);
        PVOCID.setText("ID: "+card.getID());
        PVOCID.setVisible(true);
        PVOCDescription.setText(card.getDescription());
        PVOCDescription.setVisible(true);
        PVOCColor.setText(card.getColor().name());
        PVOCColor.setVisible(true);
        Image image;
        image = new Image("/GUI/privateObj.PNG");
        privateObj.setImage(image);
        privateObj.setVisible(true);
    }
}
