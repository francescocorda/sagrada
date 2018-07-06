package it.polimi.ingsw.client.game_mode.gui;

import it.polimi.ingsw.client.communicator.Communicator;
import it.polimi.ingsw.view.GUIView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class GUIData {
    private static GUIData instance= null;
    public static synchronized GUIData getGUIData() {
        if (instance == null) {
            instance = new GUIData();
        }
        return instance;
    }
    private ActionEvent event;
    private Stage stage;
    private Communicator communicator;
    private String username;
    private GUIView view;
    private long time =-1;

    /**
     * This method is the getter of the private attribute time.
     */
    public long getTime() {
        return time;
    }

    /**
     * This method is the setter of the private attribute time.
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * This method is the getter of the private attribute event.
     */
    public ActionEvent getEvent() {
        return event;
    }

    /**
     * This method is the setter of the private attribute event.
     */
    public void setEvent(ActionEvent event) {
        this.event = event;
    }

    /**
     * This method is the getter of the private attribute stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * This method is the setter of the private attribute stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method is the setter of the private attribute communicator.
     */
    public void setCommunicator(Communicator communicator){
        this.communicator = communicator;
    }

    /**
     * This method is the getter of the private attribute communicator.
     */
    public Communicator getCommunicator(){
        return this.communicator;
    }

    /**
     * This method is the setter of the private attribute username.
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * This method is the getter of the private attribute username.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * This method is the setter of the private attribute view.
     */
    public void setView(GUIView view){
        this.view = view;
    }

    /**
     * This method is the getter of the private attribute username.
     */
    public GUIView getView(){
        return this.view;
    }
}
