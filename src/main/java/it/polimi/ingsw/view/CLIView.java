package it.polimi.ingsw.view;

import it.polimi.ingsw.model.cards.patterns.PatternCard;
import it.polimi.ingsw.model.cards.patterns.Restriction;
import it.polimi.ingsw.model.cards.private_objectives.PrivateObjectiveCard;
import it.polimi.ingsw.model.cards.public_objectives.PublicObjectiveCard;
import it.polimi.ingsw.model.cards.toolcard.ToolCard;
import it.polimi.ingsw.model.game.Dice;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.game.Table;
import it.polimi.ingsw.model.game.WindowFrame;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.server.socket.SocketVisitor;

import java.util.ArrayList;

import static it.polimi.ingsw.model.cards.patterns.PatternCard.COLUMN;
import static it.polimi.ingsw.model.cards.patterns.PatternCard.ROW;

public class CLIView extends Observable implements View {

    private String username;
    private ViewVisitor visitor;

    /**
     * creates a new {@link CLIView}
     * it does that by setting {@link #username} to null and by creating a new {@link ViewVisitor}
     */
    public CLIView() {
        visitor = new ViewVisitor(this);
        username = null;
    }

    /**
     * @return a deep copy of the {@link CLIView}.
     */
    @Override
    public CLIView copy() {
        CLIView tempView = new CLIView();
        tempView.setUsername(this.username);
        return tempView;
    }

    /**
     * sets {@link #username} as the given {@link String } username.
     * @param username : the given {@link String } username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return {@link #username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * updates the view about the given {@link String } message.
     * @param message : the given {@link String} message
     */
    @Override
    public void update(String message) {
        if (message != null) {
            displayMessage(message);
        }
    }

    /**
     * updates the view about the given {@link Observable } o.
     * @param o : the given {@link Observable} o
     */
    @Override
    public void update(Observable o) {
        o.display(visitor);
    }

    /**
     * displays the given {@link PrivateObjectiveCard} privateObjectiveCard.
     * @param privateObjectiveCard : {@link PrivateObjectiveCard} to be displayed
     */
    public void displayPrivateObjectiveCard(PrivateObjectiveCard privateObjectiveCard) {
        privateObjectiveCard.dump();
    }


    /**
     * displays the given {@link Table} table.
     * @param table : {@link Table} to be displayed
     */
    public void displayGame(Table table) {
        Player myPlayer = null;
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(""));

        for (Player p : table.getPlayers()) {
            if (!p.getName().equals(username)) {
                players.add(p);
            } else {
                myPlayer = p;
                players.set(0, p);
            }
        }

        print("\nPUBLIC OBJECTIVE CARDS:\n\n");
        for (PublicObjectiveCard publicObjectiveCard : table.getGamePublicObjectiveCards()) {
            publicObjectiveCard.dump();
        }

        print("\nROUND TRACK:\n\n");
        table.getRoundTrack().dump();

        print("\nTOOL CARDS:\n\n");
        for (ToolCard toolCard : table.getGameToolCards()) {
            toolCard.dump();
        }

        System.out.println("PRIVATE OBJECTIVE CARD: ");
        myPlayer.getPrivateObjectiveCard().dump();

        table.dumpDraftPool();
        print("\n");

        showPlayers(players);

        if (table.getActiveToolCard() != null) {
            table.getActiveToolCard().dump();
        }
        if (table.getActiveDice() != null) {
            System.out.println(table.getActiveDice().toString());
        }

        if (!table.getScoreTrack().isEmpty()) {
            table.getScoreTrack().dump();
        }
    }


    /**
     * displays the given {@link PatternCard} patternCard.
     * @param patternCard : {@link PatternCard} to be displayed
     */
    public void displayPatternCard(PatternCard patternCard) {
        patternCard.dump();
    }

    /**
     * displays a given {@link String} message.
     * @param message : {@link String} to be displayed
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * shows which is the element to be activated.
     * @param element : {@link String} that contains a reference to the element to be activated
     */
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

    /**
     * displays the given {@link Player}s' {@link WindowFrame} in horizontal.
     * @param players : the given {@link Player}
     */
    private void showPlayers(ArrayList<Player> players) {
        int playerSpace = 30;
        String verticalSeparatorSymbol = "|";
        String horizontalLine = "-----------------";
        String horizontalSeparator = "--" + verticalSeparatorSymbol + horizontalLine;
        String horizontalCoordinates = " 1  2  3  4  5";

        //level 1
        for (Player player : players) {
            printFixedLength(" Player: " + player.getName(), playerSpace);
            print(verticalSeparatorSymbol);
        }
        //newLine
        print("\n");
        //level 2
        for (Player player : players) {
            printFixedLength(" Number of tokens: " + player.getNumOfTokens(), playerSpace);
            print(verticalSeparatorSymbol);
        }
        //newLine
        print("\n");
        //level 3
        for (Player player : players) {
            printFixedLength(" Pattern: " + player.getPatternCard().getName(), playerSpace);
            print(verticalSeparatorSymbol);
        }
        //newLine
        print("\n");
        //level 4
        for (Player player : players) {
            printFixedLength(" Difficulty: " + player.getPatternCard().getDifficulty(), playerSpace);
            print(verticalSeparatorSymbol);
        }
        //newLine
        print("\n");
        //newSpace
        int i = 0;
        while (i < players.size()) {
            printFixedLength(" ", playerSpace);
            print(verticalSeparatorSymbol);
            i++;
        }
        //newLine
        print("\n");
        //level 5
        i = 0;
        while (i < players.size()) {
            printFixedLength("   " + verticalSeparatorSymbol + horizontalCoordinates, playerSpace);
            print(verticalSeparatorSymbol);
            i++;
        }
        //newLine
        print("\n");
        //level 6
        i = 0;
        while (i < players.size()) {
            printFixedLength(" " + horizontalSeparator, playerSpace);
            print(verticalSeparatorSymbol);
            i++;
        }
        //newLine
        print("\n");
        //level from 7 to 10
        for (i = 0; i < ROW; i++) {
            for (Player player : players) {
                String playerRow = windowFrameRow(player.getWindowFrame(), i, verticalSeparatorSymbol);
                printFixedLength(" " + playerRow, playerSpace);
                print(verticalSeparatorSymbol);
            }
            //newLine
            print("\n");
        }
        //newLine x2
        print("\n\n");
    }

    /**
     * displays the given {@link String} string.
     * @param string : the given {@link String} string
     */
    private void print(String string) {
        System.out.print(string);
    }

    /**
     * displays the given {@link String} word adding spaces to match the given fixedLength.
     * @param word : the given {@link String} word
     * @param fixedLength : the given fixedLength
     */
    private void printFixedLength(String word, int fixedLength) {
        String emptyDiceSymbol = "\u25FB";
        String space = new String();
        int length = word.length();
        if(word.contains(emptyDiceSymbol)){
            length -= 9*COLUMN;
        } else {
            for(String string : Dice.faces){
                if(word.contains(string)){
                    length -= 9*COLUMN;
                    break;
                }
            }
        }
        for (int i = 0; i < (fixedLength - length); i++) {
            space = space.concat(" ");
        }
        print(word);
        print(space);
    }

    /**
     * @param window : the given {@link WindowFrame}
     * @param row : the given row
     * @param verticalSeparatorSymbol : given a {@link String}
     * verticalSeparatorSymbol
     * @return the given row of the given {@link WindowFrame} adding the given a {@link String}
     * verticalSeparatorSymbol at the end of it.
     */
    private String windowFrameRow(WindowFrame window, int row, String verticalSeparatorSymbol) {
        String emptyDiceSymbol = "\u25FB";
        String string = "";
        string = string.concat((row + 1) + " " + verticalSeparatorSymbol);
        for (int j = 0; j < COLUMN; j++) {
            if (window.getDice(row + 1, j + 1) == null) {
                String escape = window.getPatternCard().getRestriction(row + 1, j + 1).escape();
                int face = escape.compareTo("\u2680") + 1;
                if (face > 0) {
                    string = string.concat(Restriction.WHITE.escape() + "[" + escape + "]" + Restriction.RESET);
                } else {
                    string = string.concat(escape + "[" + emptyDiceSymbol + "]" + Restriction.RESET);
                }
            } else
                string = string.concat(window.getDice(row + 1, j + 1).toString());
        }
        return string;
    }

}