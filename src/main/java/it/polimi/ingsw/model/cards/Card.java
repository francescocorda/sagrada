package it.polimi.ingsw.model.cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    String name;
    int ID;

    public Card(String name, int ID){
        this.name=name;
        this.ID=ID;
    }

    public Card(){}

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString(){
        return "ID: " + ID + "\nName: " + name;
    }

    public void dump(){
        System.out.println(toString());
    }
}
