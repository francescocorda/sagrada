package it.polimi.ingsw.database;

import it.polimi.ingsw.view.VirtualView;

import java.util.ArrayList;

public class VirtualViewsDataBase {
    ArrayList<VirtualView> virtualViews;
    private static VirtualViewsDataBase instance;

    /**
     * creates a {@link VirtualViewsDataBase}.
     */
    private VirtualViewsDataBase() {
        virtualViews = new ArrayList<>();
    }

    /**
     * @return the only instance of {@link VirtualViewsDataBase}.
     */
    public static synchronized VirtualViewsDataBase getVirtualViewsDataBase() {
        if (instance == null) {
            instance = new VirtualViewsDataBase();
        }
        return instance;
    }

    /**
     * @param username : the given {@link String} username
     * @return the {@link VirtualView} who's {@link VirtualView#getUsername()}
     * corresponds to the given {@link String} username
     */
    public VirtualView getVirtualView(String username){
        for(VirtualView virtualView : virtualViews){
            if(virtualView.getUsername().equals(username)){
                return virtualView;
            }
        }
        return null;
    }

    /**
     * removes the {@link VirtualView} who's {@link VirtualView#getUsername()}
     * corresponds to the given {@link String} username
     * @param username : the given {@link String} username
     */
    public void removeVirtualView(String username){
        VirtualView view = null;
        for(VirtualView virtualView : virtualViews){
            if(virtualView.getUsername().equals(username)){
                view = virtualView;
            }
        }
        virtualViews.remove(view);
    }

    /**
     * adds the given {@link VirtualView} virtualView to {@link #virtualViews}.
     * @param virtualView : the given {@link VirtualView} virtualView
     */
    public void addVirtualView(VirtualView virtualView){
        if(!contains(virtualView.getUsername())) {
            virtualViews.add(virtualView);
        }
    }

    /**
     * @param username : the given {@link String} username
     * @return true if the given {@link String} username is the same of one of the {@link VirtualView} in
     * {@link #virtualViews}; false otherwise
     */
    public boolean contains(String username){
        for(VirtualView virtualView : virtualViews){
            if(virtualView.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * shows the status of {@link VirtualViewsDataBase}
     */
    public void status(){
        //TODO eliminate
        System.out.println("VIRTUAL VIEW DATABASE:\n Status:\n - Number of views: "+virtualViews.size());
        System.out.println(" - VirtualViews's players:");
        for(VirtualView view : virtualViews){
            System.out.println("   + "+view.getUsername());
        }
    }
}
