package it.polimi.ingsw.client.game_mode.gui.lobby;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.patterns.Restriction;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.game.ScoreTrack;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.client.communicator.Communicator;
import it.polimi.ingsw.client.game_mode.gui.GUIData;
import it.polimi.ingsw.client.game_mode.gui.GUIManager;
import it.polimi.ingsw.client.game_mode.gui.table.TableManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

public class LobbyManager implements GUIManager{
    private static final int NUM_OF_TOKENS = 6;
    private  static final int NUM_OF_COL = 5;
    private  static final int NUM_OF_ROW = 4;
    private HashMap<String, Integer> comparator = null;
    private Integer count;
    private Table table;
    private String temp;
    private String activeTemp;
    private boolean flag=false;
    private MouseEvent mouseEvent = null;
    private  ActionEvent actionEvent = null;
    private HashMap<Integer, String> dices = null;
    private ArrayList<Rectangle> pattern4Items;
    private HashMap<String, String> colors = null;  //Restriction of colors
    private ArrayList<Circle> tokens1;
    private ArrayList<Circle> tokens2;
    private ArrayList<Circle> tokens3;
    private ArrayList<Circle> tokens4;
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
    @FXML Circle token1_1; @FXML Circle token1_2; @FXML Circle token1_3; @FXML Circle token1_4; @FXML Circle token1_5; @FXML Circle token1_6;
    @FXML Circle token2_1; @FXML Circle token2_2; @FXML Circle token2_3; @FXML Circle token2_4; @FXML Circle token2_5; @FXML Circle token2_6;
    @FXML Circle token3_1; @FXML Circle token3_2; @FXML Circle token3_3; @FXML Circle token3_4; @FXML Circle token3_5; @FXML Circle token3_6;
    @FXML Circle token4_1; @FXML Circle token4_2; @FXML Circle token4_3; @FXML Circle token4_4; @FXML Circle token4_5; @FXML Circle token4_6;

    /**
     * This method is called by the FXMLLoader when the file lobby.fxml is loaded.
     * It initializes all the javaFx application's items and all the attributes of the class.
     * It set the current class in the GUIData class as current javaFxController class.
     */
    @FXML
    public void initialize() {
        GUIData.getGUIData().getView().setGUIManager(this);
        PVOCName.setVisible(false);
        PVOCID.setVisible(false);
        PVOCDescription.setVisible(false);
        PVOCColor.setVisible(false);
        count = 0;
        Image back = new Image(getClass().getResourceAsStream("/GUI/wood.jpg"));
        this.background.setBackground(new Background(new BackgroundImage(back, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
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
        colors.put(Restriction.WHITE.escape(), "-fx-fill: #ffffff;");
        colors.put(Restriction.RED.escape(), "-fx-fill: #ff6a49;");
        colors.put(Restriction.GREEN.escape(), "-fx-fill: #82f87e;");
        colors.put(Restriction.PURPLE.escape(), "-fx-fill: #ee82dc;");
        colors.put(Restriction.BLUE.escape(), "-fx-fill: #82c0ed;");
        colors.put(Restriction.YELLOW.escape(), "-fx-fill: #fff486;");
        dices = new HashMap<>();
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

        date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) > 0 );
            }
        });
        PVOCName.setStyle("-fx-text-alignment: center;");
        PVOCDescription.setStyle("-fx-text-alignment: center;");
        tokens1 = new ArrayList<>();
        tokens2 = new ArrayList<>();
        tokens3 = new ArrayList<>();
        tokens4 = new ArrayList<>();
        tokens1.add(token1_1); tokens1.add(token1_2); tokens1.add(token1_3); tokens1.add(token1_4); tokens1.add(token1_5); tokens1.add(token1_6);
        tokens2.add(token2_1); tokens2.add(token2_2); tokens2.add(token2_3); tokens2.add(token2_4); tokens2.add(token2_5); tokens2.add(token2_6);
        tokens3.add(token3_1); tokens3.add(token3_2); tokens3.add(token3_3); tokens3.add(token3_4); tokens3.add(token3_5); tokens3.add(token3_6);
        tokens4.add(token4_1); tokens4.add(token4_2); tokens4.add(token4_3); tokens4.add(token4_4); tokens4.add(token4_5); tokens4.add(token4_6);
        for(int i=0; i<NUM_OF_TOKENS; i++){
            tokens1.get(i).setVisible(false);
            tokens2.get(i).setVisible(false);
            tokens3.get(i).setVisible(false);
            tokens4.get(i).setVisible(false);
        }
        if(GUIData.getGUIData().getTime()!=-1){
            date.setVisible(false);
            phrase.setVisible(false);
            joinLobby.setVisible(false);
        }
    }

    /**
     * This method is called when the joinLobby Button is pressed.
     * It sends to the server the date inserted by the user.
     */
    public void joinLobby(ActionEvent event){
        GUIData.getGUIData().setEvent(event);
        try{
            LocalDate isoDate = date.getValue();
            ChronoLocalDate chronoLocalDate = ((isoDate != null) ? date.getChronology().date(isoDate) : null);
            Communicator communicator = GUIData.getGUIData().getCommunicator();
            String username = GUIData.getGUIData().getUsername();
            try {
                long time = chronoLocalDate.toEpochDay()*24*60*60;
                time = isDateValid(time);
                GUIData.getGUIData().setTime(time);
                communicator.lobby(username, time);
                date.setVisible(false);
                joinLobby.setVisible(false);
                phrase.setVisible(false);
            } catch (NetworkErrorException e) {
                this.message.setText("Network Error! server may be DOWN!\n");
            } catch (NotValidInputException | NullPointerException e) {
                this.message.setText("Not valid Date! Retry!\n");
            }
        } catch (Exception exc){
            System.out.println("Data conversion exception: \n "+ exc);
        }
        event.consume();
    }

    /**
     * This method is called if a pattern card is pressed by the user.
     * It sends to server the related index and, consequently, it loads the table.fxml file.
     * Finally it consumes the event.
     */
    public void patternChoose(MouseEvent event){
        this.mouseEvent = event;
        String card=new String();
        GridPane source;
        source = (GridPane) event.getSource();
        if (source == pattern1) {
            card = "1";
        }
        if (source == pattern2) {
            card = "2";
        }
        if (source == pattern3) {
            card = "3";
        }
        if (source == pattern4) {
            card = "4";
        }
        try {
            GUIData.getGUIData().getCommunicator().sendMessage(card);
        } catch (NetworkErrorException e) {
            this.message.setText("Network Error! server may be DOWN!\n");
        }
        event.consume();
    }

    /**
     * This method is called in order to load table.fxml file
     */
    public void loadTable(){
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    Stage stage = GUIData.getGUIData().getStage();
                    URL location = getClass().getResource("/GUI/table.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader(location);
                    try {
                        stage.setScene(new Scene(fxmlLoader.load()));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    stage.centerOnScreen();
                    GUIManager TM = fxmlLoader.getController();
                    GUIData.getGUIData().getView().setGUIManager(TM);
                    if(temp!=null) TM.editMessage(temp);
                    if(this.table!=null) TM.updateTable(this.table);
                }
        );
    }

    /**
     * This method is called by the View class(GUIView or CLIView) in order to display a message
     * in the properly box in the javaFx application.
     * If the message is "back_to_game" is loaded the table.fxml file.
     */
    public synchronized void editMessage(String message){
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    if(message!=null)
                    {
                        this.message.appendText(message.concat("\n"));
                        if(message.equals("Welcome Back!")){
                            temp = message;
                            loadTable();
                            flag = true;
                        } else if(flag){
                            GUIData.getGUIData().getView().getGUIManager().editMessage(message);
                        }
                        else if(message.equals("Pattern card assigned.")) {
                            temp = new String();
                            temp = message;
                            flag = true;
                        } else if(flag){
                            GUIData.getGUIData().getView().getGUIManager().editMessage(message);
                        }
                    }
                }
        );
    }

    /**
     * This method is called by the View class(GUIView or CLIView) in order to display the patternCards
     * in the properly boxes in the javaFx application.
     */
    public void showPattern(PatternCard pattern){
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    GridPane grid= new GridPane();
                    ArrayList<Circle> tokens = new ArrayList();
                    ArrayList<Rectangle> items = new ArrayList<>();
                    Text name = new Text();
                    switch(count){
                        case(0):
                            grid = pattern1;
                            items = pattern1Items;
                            name = pattern1Name;
                            tokens = tokens1;
                            break;
                        case(1):
                            grid = pattern2;
                            items = pattern2Items;
                            name = pattern2Name;
                            tokens = tokens2;
                            break;
                        case(2):
                            grid = pattern3;
                            items = pattern3Items;
                            name = pattern3Name;
                            tokens = tokens3;
                            break;
                        case(3):
                            grid = pattern4;
                            items = pattern4Items;
                            name = pattern4Name;
                            tokens = tokens4;
                            break;
                    }
                    name.setText(pattern.getName());
                    grid.setVisible(true);
                    name.setVisible(true);
                    StackPane dice;
                    for(int i=1; i<=NUM_OF_ROW; i++){
                        for(int j=1; j<=NUM_OF_COL; j++){
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
                                items.get((i - 1) * (NUM_OF_COL) + (j - 1)).setStyle(colors.get(pattern.getRestriction(i, j).escape()));
                            }
                        }
                    }
                    for(int i=0; i<pattern.getDifficulty(); i++){
                        tokens.get(i).setVisible(true);
                    }
                    count++;
                }
        );
    }

    /**
     * this method is called by the view class in order to update local attribute table.
     */
    public void updateTable(Table table){
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    this.table=table;
                    if(table!=null) GUIData.getGUIData().getView().getGUIManager().updateTable(table);
                }
        );
    }

    /**
     * This method is called by the joinLobby method to check if the data selected
     * by the user is valid. It converts it into a long.
     * @returns the date converted to long.
     */
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

    /**
     * This method is called by the View class(GUIView or CLIView) in order to display the PrivateObjectiveCard
     * in the properly boxes in the javaFx application.
     */
    public void displayPrivateObjectiveCard(PrivateObjectiveCard card) {
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    PVOCName.setText(card.getName());
                    PVOCName.setVisible(true);
                    PVOCID.setText("ID: "+card.getID());
                    PVOCID.setVisible(true);
                    PVOCDescription.setText(card.getDescription());
                    PVOCDescription.setVisible(true);
                    PVOCColor.setText(card.getColor().name());
                    PVOCColor.setVisible(true);
                    Image image;
                    InputStream inputStream= this.getClass().getResourceAsStream("/GUI/privateObj.PNG");
                    image = new Image(inputStream);
                    privateObj.setImage(image);
                    privateObj.setVisible(true);
                }
        );
    }

    /**
     * This method is empty since it is used by the class ScoreTrackManager.
     */
    public void showScoreTrack(ScoreTrack scoreTrack){}

    /**
     * This method is called by the View in order to update properly activeTemp local attribute.
     */
    public void activeElement(String element){
        Platform.runLater(  //Compulsory to update gui
                () -> {
                    if(element.equals("CHOOSE_ACTION")) GUIData.getGUIData().getView().getGUIManager().activeElement(element);
                    activeTemp = element;
                    if(element.equals("START")){
                        loadTable();
                    }
                }
        );
    }
}
