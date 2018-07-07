package it.polimi.ingsw.server.socket;

import com.google.gson.Gson;
import it.polimi.ingsw.model.game.Table;

public class SocketVisitor{

    /**
     * @param table : the given {@link Table} table
     * @return a {@link Gson}  {@link String} version of the given {@link Table} table
     */
    public String visit(Table table) {
        return (new Gson()).toJson(table);
    }
}
