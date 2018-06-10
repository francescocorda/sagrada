package it.polimi.ingsw.view;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.Model.Game.Game;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Table;

import java.util.Observable;
import java.util.Scanner;

public class CLIView extends Observable implements View {

    private String username;

    private Table table;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Table) {
            Table table = (Table) o;
            this.table = table;
            if(arg == null) {
                displayGame();
            }
            else if(arg instanceof String) {
                String message = (String) arg;
                displayMessage(message);
            }
        }
    }

    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        table.getPlayers().get(0).setPrivateObjectiveCard(privateObjectiveCard);
        displayPrivateObjectiveCard();
    }

    public void displayGame() {
        Player myPlayer = null;

        for (PublicObjectiveCard publicObjectiveCard: table.getGamePublicObjectiveCards()) {
            publicObjectiveCard.dump();
        }

        for(Player p: table.getPlayers()) {
            if(!p.getName().equals(username)) {
                System.out.println("Name: " + p.getName());
                System.out.println("Number of Tokens: " + p.getNumOfTokens());
                System.out.println(p.getWindowFrame().toGameString());
            } else {
                myPlayer = p;
            }
        }

        table.getRoundTrack().dump();
        table.dumpDraftPool();
        for (ToolCard toolCard: table.getGameToolCards()) {
            toolCard.dump();
        }

        System.out.println("Name: " + myPlayer.getName());
        System.out.println("Number of Tokens: " + myPlayer.getNumOfTokens());
        System.out.println("Private Objective Card: ");
        myPlayer.getPrivateObjectiveCard().dump();
        System.out.println(myPlayer.getWindowFrame().toGameString());
        if (table.getActiveToolCard() != null) {
            table.getActiveToolCard().dump();
        }
        if (table.getActiveDice()!=null) {
            System.out.println(table.getActiveDice().toString());
        }
    }

    public void displayPrivateObjectiveCard() {
        table.getPlayers().get(0).getPrivateObjectiveCard().dump();
    }

    public void displayPatternCard(PatternCard patternCard) {
        patternCard.dump();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayGameMessage(String message) {
        System.out.println(message);
    }


}
