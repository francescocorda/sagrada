package it.polimi.ingsw.client.GUI.login;
import it.polimi.ingsw.client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/GUI/login.fxml"));
        Parent root = fxmlLoader.load();
        root.getStylesheets().add("GUI/login.css");
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root, 500, 600));
        primaryStage.show();
    }

    public void showGUI() {
        launch();
    }
}
