package it.polimi.ingsw.client.GUI.lobby;

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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

public class LobbyManager implements GUIManager{
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
    @FXML
    AnchorPane background;

    public void joinLobby(javafx.event.ActionEvent event){
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
        } catch (NetworkErrorException e) {
            this.message.setText("Network Error! Server may be DOWN!\n");
        } catch (NotValidInputException | NullPointerException e) {
            this.message.setText("Not valid Date! Retry!\n");
        }
    }
    @FXML
    public void initialize(){
        GUIData.getGUIData().getView().setGUIManager(this);
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        HM=new HashMap<>();
        HM.put(0, card1);
        HM.put(1, card2);
        HM.put(2, card3);
        HM.put(3, card4);
        count=0;
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

    public void showPattern(int ID){
        if(HM==null) System.out.println("HM null");
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
        }
    }

    private void setImage(String path){
        image = new Image(Client.class.getResourceAsStream(path));
        HM.get(count).setImage(image);
        HM.get(count).setVisible(true);
        count++;
    }
    public void updateTable(Table table){
        Image imageToBeUpdated = new Image(PVOCs.get(table.getPlayers().get(0).getPrivateObjectiveCard().getID()));
        privateObj.setImage(imageToBeUpdated);
        privateObj.setVisible(true);
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
}
