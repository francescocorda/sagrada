package it.polimi.ingsw;

import java.util.ArrayList;

public class MessageReader {
    private ArrayList<String> parts;

    MessageReader(String message){
        if(message==null)
            throw new NullPointerException();
        int lastSymbol=0;
        int secondLastSymbol;
        parts = new ArrayList<>();
        while(lastSymbol<message.length()-1) {
            secondLastSymbol=lastSymbol;
            lastSymbol= message.indexOf('<', secondLastSymbol+1);
            if(secondLastSymbol==0){
                if(lastSymbol== -1){
                    parts.add(message);
                    break;
                }
                parts.add(message.substring(secondLastSymbol, lastSymbol));
            }
            secondLastSymbol=lastSymbol;
            lastSymbol= message.indexOf('>', secondLastSymbol+1);
            parts.add(message.substring(secondLastSymbol+1, lastSymbol));
        }
    }

    public boolean hasNext(){
        return !parts.isEmpty();
    }

    public String getNext(){
        if(parts.isEmpty())
            throw new NullPointerException();
        return parts.remove(0);
    }
}
