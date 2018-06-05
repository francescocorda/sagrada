package it.polimi.ingsw.client.GUI.login;

import it.polimi.ingsw.client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private SplitMenuButton connection;
    @FXML
    private TextField IPaddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Button login;

    @FXML
    public void loginAction(MouseEvent event) throws IOException{
        System.out.println("Welcome "+username.getText()+"!");
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(Client.class.getResource("/GUI/table.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.centerOnScreen();
        stage.setMaximized(true);
    }
    @FXML
    public void connectionRMI(){
        IPaddress.setVisible(true);
        serverPort.setVisible(false);
        login.setDisable(false);
        connection.setText("RMI");
    }
    @FXML
    public void connectionSocket(){
        IPaddress.setVisible(true);
        serverPort.setVisible(true);
        login.setDisable(false);
        connection.setText("socket");
    }
    @FXML
    public void initialize(){
        IPaddress.setVisible(false);
        serverPort.setVisible(false);
        login.setDisable(true);
    }
}
