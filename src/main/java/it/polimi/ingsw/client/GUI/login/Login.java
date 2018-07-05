package it.polimi.ingsw.client.GUI.login;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI.GUIData;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Login extends Application {
    private static final int WIDTH=500;
    private static final int HEIGHT=650;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("/GUI/login.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Sagrada");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        GUIData.getGUIData().setStage(primaryStage);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
    }

    public void showGUI() {
        launch();
    }
}
