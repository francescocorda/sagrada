package it.polimi.ingsw.controller;

import java.util.ArrayList;

public class EndState extends State {

    public EndState(Controller controller) {
        super(controller);
    }

    @Override
    public void handleEvent(String username, ArrayList<String> commands) {
    }
}
