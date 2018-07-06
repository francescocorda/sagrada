package it.polimi.ingsw.view;

import it.polimi.ingsw.model.game.Table;

public class ViewVisitor{

    private View view;

    public ViewVisitor(View view) {
        this.view = view;
    }

    public void visit(Table table) {
        view.displayGame(table);
    }
}
