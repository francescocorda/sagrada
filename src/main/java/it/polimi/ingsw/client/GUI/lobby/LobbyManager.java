package it.polimi.ingsw.client.GUI.lobby;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Comunicator;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.GUI.GUIManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;

public class LobbyManager implements GUIManager{
    private Image image;
    private ImageView source;
    private Integer count;
    private HashMap<Integer, ImageView> HM;
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
    public void joinLobby(javafx.event.ActionEvent event){
        Comunicator comunicator = GUIData.getGUIData().getComunicator();
        String username = GUIData.getGUIData().getUsername();
        try {
            comunicator.lobby(username, Long.parseLong("20"));
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize(){
        GUIData.getGUIData().getView().setGUIManager(this);
        this.message.setText("Welcome to the lobby!\n");
        card1.setVisible(false);
        card2.setVisible(false);
        card3.setVisible(false);
        card4.setVisible(false);
        HM=new HashMap<Integer, ImageView>();
        HM.put(0, card1);
        HM.put(1, card2);
        HM.put(2, card3);
        HM.put(3, card4);
        count=0;
    }

    @FXML
    public void mousePressed(MouseEvent event){
        String card=GUIData.getGUIData().getUsername();
        source = (ImageView) event.getSource();
        if (source == card1) {
            card = card.concat("/").concat("0");
        }
        if (source == card2) {
            card = card.concat("/").concat("1");
        }
        if (source == card3) {  //to modify
            card = card.concat("/").concat("2");
        }
        if (source == card4) {  //to modify
            card = card.concat("/").concat("3");
        }
        try {
            GUIData.getGUIData().getComunicator().sendMessage(card);
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/table.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setMaximized(true);
    }

    public void editMessage(String message){
        this.message.setText(this.message.getText().concat(message.concat("\n")));
    }

    public void showPattern(int ID){
        if(HM==null) System.out.println("HM null");
        if(HM.get(0)==null) System.out.println("0 null");
        if(HM.get(1)==null) System.out.println("1 null");
        if(HM.get(2)==null) System.out.println("2 null");
        if(HM.get(3)==null) System.out.println("3 null");
        switch (ID) {
            case(1): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern1.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(2): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern2.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(3): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern3.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(4): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern4.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(5): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern5.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(6): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern6.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(7): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern7.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(8): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern8.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(9): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern9.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }case(10): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern10.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(11): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern11.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }case(12): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern12.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(13): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern13.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(14): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern14.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(15): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern15.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }case(16): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern16.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }case(17): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern17.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }case(18): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern18.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }case(19): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern19.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }case(20): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern20.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(21): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern21.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(22): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern22.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(23): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern23.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
            case(24): {
                image = new Image(Client.class.getResourceAsStream("/GUI/pattern24.PNG"));
                HM.get(count).setImage(image);
                HM.get(count).setVisible(true);
                count++;
                break;
            }
        }
    }
}
