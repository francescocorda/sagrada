package it.polimi.ingsw.client.GUI.lobby;

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
import javafx.stage.Stage;
import java.io.IOException;

public class LobbyManager implements GUIManager{
    @FXML
    Button table;
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
        table.setDisable(true);
    }
    @FXML
    public void showTable(javafx.event.ActionEvent event){
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
        if (message.compareTo("Scegli tra una delle seguenti PatternCard: (0-1)")==0){
            table.setDisable(false);
        }
    }
}
