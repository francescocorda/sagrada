package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.view.GUIView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class GUIData {
    private ActionEvent event;
    private Stage stage;
    private Communicator communicator;
    private String username;
    private GUIView view;
    private static GUIData instance= null;
    public static synchronized GUIData getGUIData() {
        if (instance == null) {
            instance = new GUIData();
        }
        return instance;
    }

    public ActionEvent getEvent() {
        return event;
    }
    public void setEvent(ActionEvent event) {
        this.event = event;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCommunicator(Communicator communicator){
        this.communicator = communicator;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public Communicator getCommunicator(){
        return this.communicator;
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
