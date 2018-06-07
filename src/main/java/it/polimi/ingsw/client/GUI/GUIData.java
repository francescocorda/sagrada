package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Comunicator;
import it.polimi.ingsw.view.GUIView;
import it.polimi.ingsw.view.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIData {
    private Stage stage;
    private Comunicator comunicator;
    private String username;
    private GUIView view;
    private static GUIData instance= null;
    public static synchronized GUIData getGUIData() {
        if (instance == null) {
            instance = new GUIData();
        }
        return instance;
    }
    public void setComunicator(Comunicator comunicator){
        this.comunicator = comunicator;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public Comunicator getComunicator(){
        return this.comunicator;
    }
    public String getUsername(){
        return this.username;
    }
    public void setView(GUIView view){
        this.view = view;
    }
    public GUIView getView(){
        return this.view;
    }
}
