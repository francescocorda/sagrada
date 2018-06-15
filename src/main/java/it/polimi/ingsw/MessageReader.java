package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageReader {
    private ArrayList<String> parts;

    public MessageReader(String message){
        if(message==null)
            throw new NullPointerException();
        parts = new ArrayList<>(Arrays.asList(message.split("\\s*/\\s*")));
    }

    /**
     * tells if the {@link MessageReader#parts} has yet something that has to be read.
     * @return true if there's something to be read or false if there's not.
     */
    public boolean hasNext(){
        return !parts.isEmpty();
    }

    /**
     * returns the string in index #0 of {@link MessageReader#parts} or throws NullPointerException
     * @throws NullPointerException if it is invoked when empty
     * @return String at index 0 of {@link MessageReader#parts}
     */
    public String getNext(){
        if(parts.isEmpty())
            throw new NullPointerException();
        return parts.remove(0);
    }
}
