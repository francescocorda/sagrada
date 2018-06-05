package it.polimi.ingsw.client.GUI.completeTable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TableMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("table.fxml"));
        //root.getStylesheets().add("/sample/table/login.css");
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Table");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

