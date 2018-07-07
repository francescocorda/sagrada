package it.polimi.ingsw.model.cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    String name;
    int ID;

    /**
     * creates a new {@link Card} from the given parameters.
     * @param name : the given {@link String} name.
     * @param ID : the given int ID.
     */
    public Card(String name, int ID){
        this.name=name;
        this.ID=ID;
    }

    /**
     * creates a new {@link Card}.
     */
    public Card(){}

    /**
     * sets {@link #ID} as the given int ID.
     * @param ID : the given int ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * sets {@link #name} as the given {@link String} name.
     * @param name : the given {@link String} name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return {@link #name}.
     */
    public String getName() {
        return name;
    }

    /**
     * @return {@link #ID}.
     */
    public int getID() {
        return ID;
    }

    /**
     * @return the representation of this {@link Card}.
     */
    @Override
    public String toString(){
        return "ID: " + ID + "\nName: " + name;
    }

    /**
     * displays {@link #toString()}.
     */
    public void dump(){
        System.out.println(toString());
    }
}
