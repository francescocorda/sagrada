package it.polimi.ingsw.client.GUI.login;

import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.client.CommunicatorRMI;
import it.polimi.ingsw.client.CommunicatorSocket;
import it.polimi.ingsw.client.GUI.GUIData;
import it.polimi.ingsw.client.GUI.GUIManager;
import it.polimi.ingsw.exceptions.NetworkErrorException;
import it.polimi.ingsw.exceptions.NotValidInputException;
import it.polimi.ingsw.view.GUIView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginManager implements GUIManager{
    private Communicator communicator;

    private static GUIView view;
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
    public void loginAction(MouseEvent event) {
        System.out.println("Welcome " + username.getText() + "!");
        if (connection.getText().equals("RMI")) {
            view = new GUIView();
            view.setGUIManager(this);
            communicator = new CommunicatorRMI(view);
            ArrayList<String> parameters = new ArrayList();
            parameters.add(IPaddress.getText());
            try {
                communicator.initialize(parameters);
                communicator.login(username.getText(), password.getText());
                GUIData.getGUIData().setCommunicator(communicator);
                GUIData.getGUIData().setUsername(username.getText());
                GUIData.getGUIData().setView(view);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/lobby.fxml"));
                //initializeLobby();
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/lobby.fxml"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.centerOnScreen();
            } catch (NetworkErrorException | NotValidInputException e) {
                e.printStackTrace();
            }
        } else if (connection.getText().equals("socket")) {
            view = new GUIView();
            view.setGUIManager(this);
            communicator = new CommunicatorSocket(view);
            ArrayList<String> parameters = new ArrayList();
            parameters.add(IPaddress.getText());
            parameters.add(serverPort.getText());
            try {
                communicator.initialize(parameters);
                communicator.login(username.getText(), password.getText());
                GUIData.getGUIData().setCommunicator(communicator);
                GUIData.getGUIData().setUsername(username.getText());
                GUIData.getGUIData().setView(view);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/lobby.fxml"));
                //initializeLobby();
                try {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/lobby.fxml"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.centerOnScreen();
            } catch (NetworkErrorException | NotValidInputException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void connectionRMI() {
        IPaddress.setVisible(true);
        serverPort.setVisible(false);
        login.setDisable(false);
        connection.setText("RMI");
    }

    @FXML
    public void connectionSocket() {
        IPaddress.setVisible(true);
        serverPort.setVisible(true);
        login.setDisable(false);
        connection.setText("socket");
    }

    @FXML
    public void initialize() {
        IPaddress.setVisible(false);
        serverPort.setVisible(false);
        login.setDisable(true);
    }
    public void editMessage(String message){}
    public void showPattern(int ID){}
    public void updateTable(Table table){};
}

