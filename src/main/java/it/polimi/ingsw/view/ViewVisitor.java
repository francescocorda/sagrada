package it.polimi.ingsw.view;

import it.polimi.ingsw.model.game.Table;

public class ViewVisitor{

    private View view;

    /**
     * creates a new {@link ViewVisitor} from the given {@link View}.
     * @param view : the given {@link View}
     */
    public ViewVisitor(View view) {
        this.view = view;
    }

    /**
     * displays the given {@link Table} table.
     * @param table : the given {@link Table} table
     */
    public void visit(Table table) {
        view.displayGame(table);
    }
}
