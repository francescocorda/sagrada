package it.polimi.ingsw.view;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.Model.Game.Table;
import it.polimi.ingsw.PlayerData;
import it.polimi.ingsw.PlayerDatabase;
import it.polimi.ingsw.connection.ConnectionMode;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.rmi.RemoteException;
import java.util.Observable;

public class VirtualView extends Observable implements View {

    private PlayerData playerData;
    private Gson gson;

    public VirtualView(PlayerData playerData) {
        super();
        this.playerData = playerData;
        gson = new Gson();
    }

    public String getUsername() {
        return playerData.getUsername();
    }


    @Override
    public void displayGame() {
        if (playerData.getCurrentConnectionMode() == ConnectionMode.RMI){
            try {
                PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).displayGame();
            } catch (RemoteException | NotFound e) {
                e.printStackTrace();
            }
        } else {
            playerData.getClientSocket().sendMessage("game/displayGame");
        }

    }

    @Override
    public void displayMessage(String message) {
        if (playerData.getCurrentConnectionMode() == ConnectionMode.RMI){
            try {
                PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).send(message);
            } catch (RemoteException | NotFound e) {
                e.printStackTrace();
            }
        } else {
            playerData.getClientSocket().sendMessage("game/message/"+message);
        }
    }

    @Override
    public void displayGameMessage(String message) {
        if (playerData.getCurrentConnectionMode() == ConnectionMode.RMI){
            try {
                PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).send(message);
            } catch (RemoteException | NotFound e) {
                e.printStackTrace();
            }
        } else {
            playerData.getClientSocket().sendMessage("game/message/"+message);
        }
    }


    @Override
    public void setPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {

    }

    @Override
    public void displayPrivateObjectiveCard() {

    }

    @Override
    public void displayPatternCard(PatternCard patternCard) {
        if (playerData.getCurrentConnectionMode() == ConnectionMode.RMI){
            try {
                PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).sendPatternCard(patternCard);
            } catch (RemoteException | NotFound e) {
                e.printStackTrace();
            }
        } else {
            String patternCardJSON = gson.toJson(patternCard);
            playerData.getClientSocket().sendMessage("game/pattern_card/"+patternCardJSON);
        }

    }

    @Override
    public void update(Observable o, Object arg) {
        if (playerData.getCurrentConnectionMode() == ConnectionMode.RMI){
            try {
                PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).update(o, arg);
            } catch (RemoteException | NotFound e) {
                e.printStackTrace();
            }
        } else {
            if(o instanceof Table){
                String observable = ((Table) o).toJson();
                System.out.println("OBSERVABLE: "+observable);
                String object = gson.toJson(arg);
                playerData.getClientSocket().sendMessage("game/update/"+observable+"/"+object);
            }
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}