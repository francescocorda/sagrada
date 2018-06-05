package it.polimi.ingsw.client.GUI.userInterface;

import it.polimi.ingsw.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class UserInterfaceController {
    @FXML
    RadioButton GUI;
    @FXML
    public void CLIPressed(){
        System.out.println("Connection mode: CLI");
    }
    @FXML
    public void GUIPressed(MouseEvent event) throws IOException {
        System.out.println("Connection mode: GUI");
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Client.class.getResource("/GUI/login.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.centerOnScreen();
    }
    @FXML
    public void initialize(){
    }
}
