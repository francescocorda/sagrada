package it.polimi.ingsw.server.socket;

import com.google.gson.Gson;
import it.polimi.ingsw.model.game.Table;

public class SocketVisitor{

    public String visit(Table table) {
        return (new Gson()).toJson(table);
        //return table.toJson();
    }
}
