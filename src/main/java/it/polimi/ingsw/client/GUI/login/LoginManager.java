package it.polimi.ingsw.client.GUI.login;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.ScoreTrack;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static it.polimi.ingsw.client.CLI.CLI.DEFAULT_SERVER;
import static it.polimi.ingsw.client.CLI.CLI.DEFAULT_SERVER_PORT;

public class LoginManager implements GUIManager{
    private Communicator communicator;

    private static GUIView view;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private MenuButton connection;
    @FXML
    private TextField IPaddress;
    @FXML
    private TextField serverPort;
    @FXML
    private Button login;
    @FXML
    private ImageView background;
    private static String DEFAULT_PASSWORD = "default";

    @FXML
    public void loginAction(MouseEvent event) {
        if (!connection.getText().equals("socket")) {
            view = new GUIView();
            view.setGUIManager(this);
            communicator = new CommunicatorRMI(view);
            ArrayList<String> parameters = new ArrayList();
            if(IPaddress.getText().equals("")) IPaddress.setText(DEFAULT_SERVER);
            parameters.add(IPaddress.getText());
            try {
                communicator.initialize(parameters);
                if(password.getText().equals("")) password.setText(DEFAULT_PASSWORD);
                communicator.login(username.getText(), password.getText());
                GUIData.getGUIData().setCommunicator(communicator);
                GUIData.getGUIData().setUsername(username.getText());
                GUIData.getGUIData().setView(view);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    view.setUsername(username.getText());
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/GUI/lobby.fxml"))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.centerOnScreen();
            } catch (NetworkErrorException | NotValidInputException e) {
                e.printStackTrace();
            }
        } else {
            view = new GUIView();
            view.setGUIManager(this);
            communicator = new CommunicatorSocket(view);
            ArrayList<String> parameters = new ArrayList();
            if(IPaddress.getText().equals("")) IPaddress.setText(DEFAULT_SERVER);
            if(serverPort.getText().equals("")) serverPort.setText(DEFAULT_SERVER_PORT);
            parameters.add(IPaddress.getText());
            parameters.add(serverPort.getText());
            try {
                communicator.initialize(parameters);
                if(password.getText().equals("")) password.setText(DEFAULT_PASSWORD);
                communicator.login(username.getText(), password.getText());
                GUIData.getGUIData().setCommunicator(communicator);
                GUIData.getGUIData().setUsername(username.getText());
                GUIData.getGUIData().setView(view);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        serverPort.setVisible(true);
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
        Image image;
        InputStream inputStream= this.getClass().getResourceAsStream("/GUI/loginBackground.jpg");
        image = new Image(inputStream);
        background.setImage(image);
        background.setVisible(true);
        IPaddress.setVisible(false);
        serverPort.setVisible(false);
        login.setDisable(true);
    }
    public void editMessage(String message){}
    public void showPattern(PatternCard pattern){}
    public void updateTable(Table table){}
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard){}
    public void showScoreTrack(ScoreTrack scoreTrack){}
    public void activeElement(String element){}
}

