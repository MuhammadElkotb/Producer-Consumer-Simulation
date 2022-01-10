package Controllers;

import java.util.HashMap;

public class EventManager {
    private HashMap<String, Observer> listeners;
    private static EventManager instance = null;
    private EventManager(){
        this.listeners = new HashMap<>();
    }
    public static EventManager getInstance(){
        if(instance == null){
            return new EventManager();
        }else {
            return instance;
        }
    }
    public void addListener(String concern, Observer listener) {
        this.listeners.put(concern,listener);
    }

    public void removeListener(String concern,Observer listener) {
        this.listeners.remove(concern,listener);
    }
    public void notify(String concern, Network network) {
        if(concern.contains("Machine")) {
            listeners.get(concern).update(network);
        }
        else if(concern.contains("Queue")){
            listeners.get(concern).update(network);
        }
    }

}
