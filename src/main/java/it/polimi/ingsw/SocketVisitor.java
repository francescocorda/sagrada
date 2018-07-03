package it.polimi.ingsw;

import com.google.gson.Gson;
import it.polimi.ingsw.Model.Game.Table;

public class SocketVisitor{

    public String visit(Table table) {
        return (new Gson()).toJson(table);
        //return table.toJson();
    }
}
