package it.polimi.ingsw.view;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Cards.PublicObjectives.PublicObjectiveCard;
import it.polimi.ingsw.Model.Cards.toolcard.ToolCard;
import it.polimi.ingsw.Model.Game.Player;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.SocketVisitor;

public class CLIView extends Observable implements View {

    private String username;
    private ViewVisitor visitor;

    public CLIView() {
        visitor = new ViewVisitor(this);
        username = null;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void update(String message) {
        if(message != null) {
            displayMessage(message);
        }
    }

    @Override
    public void update(Observable o) {
        o.display(visitor);
}


    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        privateObjectiveCard.dump();
    }


    public void displayGame(Table table) {
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

        if (!table.getScoreTrack().isEmpty()) {
            table.getScoreTrack().dump();
        }
    }


    public void displayPatternCard(PatternCard patternCard) {
        patternCard.dump();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void activeTableElement(String element) {
        System.out.println(element);
    }


    @Override
    public void display(ViewVisitor visitor) {

    }

    @Override
    public String convert(SocketVisitor visitor) {
        return null;
    }
}