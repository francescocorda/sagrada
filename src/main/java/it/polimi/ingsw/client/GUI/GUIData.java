package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.Communicator;
import it.polimi.ingsw.view.GUIView;
import javafx.stage.Stage;

public class GUIData {
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
