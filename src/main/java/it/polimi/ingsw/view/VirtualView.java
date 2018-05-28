package it.polimi.ingsw.view;

import it.polimi.ingsw.Model.Cards.Patterns.PatternCard;
import it.polimi.ingsw.Model.Cards.PrivateObjectives.PrivateObjectiveCard;
import it.polimi.ingsw.PlayerData;
import it.polimi.ingsw.PlayerDatabase;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.rmi.RemoteException;
import java.util.Observable;

public class VirtualView extends Observable implements View {

    private PlayerData playerData;

    public VirtualView(PlayerData playerData) {
        super();
        this.playerData = playerData;
    }

    public String getUsername() {
        return playerData.getUsername();
    }


    @Override
    public void displayGame() {
        try {
            PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).displayGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
    }

    @Override
    public void displayMessage(String message) {
        try {
            PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
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
        try {
            PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).sendPatternCard(patternCard);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            PlayerDatabase.getPlayerDatabase().getClientRMI(playerData.getUsername()).update(o, arg);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
