package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageReader {
    private ArrayList<String> parts;

    public MessageReader(String message){
        if(message==null)
            throw new NullPointerException();
        int lastSymbol=0;
        int secondLastSymbol;

        parts = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
        /*
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
            if(lastSymbol==-1){
                break;
            }
            secondLastSymbol=lastSymbol;
            lastSymbol= message.indexOf('>', secondLastSymbol+1);
            if(lastSymbol == -1){
                break;
            }
            else
                parts.add(message.substring(secondLastSymbol+1, lastSymbol));
        }
        */
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
